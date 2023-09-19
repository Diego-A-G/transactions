package com.example.transactions.repository.implementation

import com.example.transactions.Constants
import com.example.transactions.persistence.database.CrediBankDatabase
import com.example.transactions.persistence.entities.AnnulmentEntity
import com.example.transactions.repository.declaration.IAuthorizationRepository
import com.example.transactions.repository.dto.AnnulRequestDTO
import com.example.transactions.repository.dto.AnnulResponseDTO
import com.example.transactions.repository.dto.AuthResponseDTO
import com.example.transactions.repository.mappers.AuthorizationDTOMapper
import com.example.transactions.repository.mappers.AuthorizationEntityMapper
import com.example.transactions.services.ITransactionsServices
import com.example.transactions.services.ServiceRest
import com.example.transactions.ui.vos.TransactionVO

class AuthorizationRepository(
    private val serviceRest: ServiceRest,
    private val database: CrediBankDatabase
) : IAuthorizationRepository {

    override suspend fun authTransaction(transactionVO: TransactionVO) {
        sendTransaction(transactionVO)
    }

    private suspend fun sendTransaction(transactionVO: TransactionVO) {
        val dto = AuthorizationDTOMapper().VOToBussiness(transactionVO)
        val retrofit = serviceRest.getClientWithAuthHeader(
            Constants.URL,
            transactionVO.commerceCode,
            transactionVO.terminalCode
        )
        val apiClient = retrofit?.create(ITransactionsServices::class.java)
        val response = apiClient?.sendAuthorization(dto)
        if (response !is AuthResponseDTO) throw Exception()
        transactionVO.receiptId = response.receiptId
        transactionVO.rrn = response.rrn
        transactionVO.statusCode = response.statusCode
        transactionVO.statusDescription = response.statusDescription
        saveTransaction(transactionVO)
    }

    override suspend fun saveTransaction(transactionVO: TransactionVO) {
        val entity = AuthorizationEntityMapper().VOToBussiness(transactionVO)
        database.getAuthorizationDao().insertAuthorization(entity)
    }

    override suspend fun getTransactions(): List<TransactionVO> {
        val success = database.getAuthorizationDao().getAllAuthorizations().map {
            AuthorizationEntityMapper().bussinessToVO(it)
        }
        return success
    }

    override suspend fun getTransactionByReceipt(receipt: String): TransactionVO {
        val entity =
            database.getAuthorizationDao().getAuthorizationById(receipt) ?: throw Exception()
        return AuthorizationEntityMapper().bussinessToVO(entity)
    }

    override suspend fun getAuthTransactions(): List<TransactionVO> {
        return database.getAuthorizationDao().getAllAuthorizations().map {
            AuthorizationEntityMapper().bussinessToVO(it)
        }
    }

    override suspend fun cancelTransaction(
        receiptId: String,
        rrn: String,
        commerceCode: String,
        terminalCode: String
    ) {
        val annulment = AnnulRequestDTO(receiptId, rrn)
        try {
            val retrofit = serviceRest.getClientWithAuthHeader(
                Constants.URL,
                commerceCode,
                terminalCode
            )
            val response =
                retrofit!!.create(ITransactionsServices::class.java).sendAnnulation(annulment)
            if (response !is AnnulResponseDTO) throw Exception()
            val annulmentEntity = AnnulmentEntity(
                receiptId = receiptId,
                rrn = rrn,
                statusCode = response.statusCode,
                statusDescription = response.statusDescription
            )

            database.getAnnulmentDao().insertAnnulment(annulmentEntity)
            database.getAuthorizationDao().updateAuthorizationStatus(
                receiptId, "99", "Denegada"
            )
            //TODO eliminar annulment dao por redundancia
        } catch (ex: Exception) {
            throw ex
        }
    }
}