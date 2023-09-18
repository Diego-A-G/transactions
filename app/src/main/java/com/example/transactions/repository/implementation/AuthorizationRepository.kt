package com.example.transactions.repository.implementation

import com.example.transactions.persistence.database.CrediBankDatabase
import com.example.transactions.persistence.entities.AnnulmentEntity
import com.example.transactions.repository.declaration.IAuthorizationRepository
import com.example.transactions.repository.dto.AnnulRequestDTO
import com.example.transactions.repository.dto.AnnulResponseDTO
import com.example.transactions.repository.dto.AuthResponseDTO
import com.example.transactions.repository.mappers.AuthorizationDTOMapper
import com.example.transactions.repository.mappers.AuthorizationEntityMapper
import com.example.transactions.services.ITransactionsServices
import com.example.transactions.ui.vos.TransactionVO

class AuthorizationRepository(
    private val apiClient: ITransactionsServices,
    private val database: CrediBankDatabase
) : IAuthorizationRepository {

    override suspend fun authTransaction(transactionVO: TransactionVO) {
        val dto = AuthorizationDTOMapper().VOToBussiness(transactionVO)
        val response = apiClient.sendAuthorization(dto)
        if (response !is AuthResponseDTO) throw Exception()
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

    override suspend fun cancelTransaction(receiptId: String, rrn: String) {
        val annulment = AnnulRequestDTO(receiptId, rrn)
        val response = apiClient.sendAnnulation(annulment)
        if (response !is AnnulResponseDTO) throw Exception()
        val annulmentEntity = AnnulmentEntity(
            receiptId = receiptId,
            rrn = rrn,
            statusCode = response.statusCode,
            statusDescription = response.statusDescription
        )
        database.getAnnulmentDao().insertAnnulment(annulmentEntity)
    }
}