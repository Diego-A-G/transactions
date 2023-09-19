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
        //sendTransaction(transactionVO)
        saveTransaction(createAuthConfirmedInfo(transactionVO))
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
        //cancelService(receiptId,rrn,commerceCode,terminalCode)
        val annulmentEntity = AnnulmentEntity(
            receiptId = receiptId,
            rrn = rrn,
            statusCode = "99",
            statusDescription = "Denegada"
        )

        database.getAnnulmentDao().insertAnnulment(annulmentEntity)
        database.getAuthorizationDao().updateAuthorizationStatus(
            receiptId, "99", "Denegada"
        )
        //TODO eliminar annulment dao por redundancia
    }

    private suspend fun cancelService(
        receiptId: String,
        rrn: String,
        commerceCode: String,
        terminalCode: String
    ) {
        val annulment = AnnulRequestDTO(receiptId, rrn)
        val retrofit = serviceRest.getClientWithAuthHeader(
            Constants.URL,
            commerceCode,
            terminalCode
        )
        val apiClient = retrofit?.create(ITransactionsServices::class.java)
        val response = apiClient?.sendAnnulation(annulment)
        if (response !is AnnulResponseDTO) throw Exception()
    }
}