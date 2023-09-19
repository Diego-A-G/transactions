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
import java.util.UUID

class AuthorizationRepository(
    private val serviceRest: ServiceRest,
    private val database: CrediBankDatabase
) : IAuthorizationRepository {

    override suspend fun authTransaction(transactionVO: TransactionVO) {
        sendTransaction(transactionVO)
    }

    private suspend fun sendTransaction(transactionVO: TransactionVO) {
        val dto = AuthorizationDTOMapper().VOToBussiness(transactionVO)
        val transaction = createAuthConfirmedInfo(transactionVO)
        try {
            val retrofit = serviceRest.getClientWithAuthHeader(
                Constants.URL,
                transaction.commerceCode,
                transaction.terminalCode
            )
            val apiClient = retrofit?.create(ITransactionsServices::class.java)
            val response = apiClient?.sendAuthorization(dto)
            if (response !is AuthResponseDTO) throw Exception()
            transaction.receiptId = response.receiptId
            transaction.rrn = response.rrn
            transaction.statusCode = response.statusCode
            transaction.statusDescription = response.statusDescription
        } catch (ex: Exception) {
            throw Exception(ex)
        } finally {
            saveTransaction(transaction)
        }
    }

    override suspend fun saveTransaction(transactionVO: TransactionVO) {
        val entity = AuthorizationEntityMapper().VOToBussiness(transactionVO)
        database.getAuthorizationDao().insertAuthorization(entity)
    }

    private fun createAuthConfirmedInfo(transactionVO: TransactionVO): TransactionVO {
        transactionVO.receiptId = UUID.randomUUID().toString()
        transactionVO.rrn = UUID.randomUUID().toString()
        transactionVO.statusCode = "00"
        transactionVO.statusDescription = "Aprobada"
        return transactionVO
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
        val annulmentEntity = AnnulmentEntity(
            receiptId = receiptId,
            rrn = rrn,
            statusCode = "00",
            statusDescription = "Aprovada"
        )
        try {
            val retrofit = serviceRest.getClientWithAuthHeader(
                Constants.URL,
                commerceCode,
                terminalCode
            )
            val response =
                retrofit!!.create(ITransactionsServices::class.java).sendAnnulation(annulment)
            if (response !is AnnulResponseDTO) throw Exception()
            annulmentEntity.statusCode = response.statusCode
            annulmentEntity.statusDescription = response.statusDescription
        } catch (ex: Exception) {
            throw ex
        } finally {
            database.getAnnulmentDao().insertAnnulment(annulmentEntity)
            database.getAuthorizationDao().updateAuthorizationStatus(
                receiptId, "99", "Denegada"
            )
        }
    }
}