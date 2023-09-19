package com.example.transactions.repository.implementation

import com.example.transactions.persistence.database.CrediBankDatabase
import com.example.transactions.repository.declaration.IAuthorizationRepository
import com.example.transactions.repository.mappers.AuthorizationDTOMapper
import com.example.transactions.repository.mappers.AuthorizationEntityMapper
import com.example.transactions.services.ServiceRest
import com.example.transactions.ui.vos.TransactionVO

class AuthorizationRepository(
    private val serviceRest: ServiceRest,
    private val database: CrediBankDatabase
) : IAuthorizationRepository {

    override suspend fun authTransaction(transactionVO: TransactionVO) {
        val dto = AuthorizationDTOMapper().VOToBussiness(transactionVO)
//        val retrofit = serviceRest.getClientWithAuthHeader(
//            Constants.URL,
//            transactionVO.commerceCode,
//            transactionVO.terminalCode)
//        val apiClient = retrofit?.create(ITransactionsServices::class.java)
//        val response = apiClient?.sendAuthorization(dto)
//        if (response !is AuthResponseDTO) throw Exception()
        saveTransaction(createAuthConfirmedInfo(transactionVO))
    }

    override suspend fun saveTransaction(transactionVO: TransactionVO) {
        val entity = AuthorizationEntityMapper().VOToBussiness(transactionVO)
        database.getAuthorizationDao().insertAuthorization(entity)
    }

    private fun createAuthConfirmedInfo(transactionVO: TransactionVO): TransactionVO {
        transactionVO.receiptId = "2cbfcb81-2692-4a98-8487-e42e3d3ae127"
        transactionVO.rrn = "402bc4ca-b820-4777-accc-2e4bee8d86e6"
        transactionVO.statusCode = "00"
        transactionVO.statusDescription = "Aprobada"
        return transactionVO
    }

    private fun createHistoryTransactions(): List<TransactionVO> {
        return listOf(
            TransactionVO(
                "1", "000123", "000ABC", "0",
                "1234567890123456", "b551386a-171f-4f1c-b0be-7501e2930e96",
                "ab05539d-c870-41fc-afb5-75e82574e002", "00", "Aprobada"
            ),
            TransactionVO(
                "2", "000123", "000ABC", "10",
                "1234567890123456", "1bbabb20-d294-4a6a-951c-7451e30875bb",
                "228c84f9-dfea-4cd6-8aa8-48e23bbebb4d", "00", "Aprobada"
            ),
            TransactionVO(
                "3", "000123", "000ABC", "100",
                "1234567890123456", "b47b157b-1880-4b3d-9bf0-d5c4ddae5833",
                "ffdea430-06a8-4d40-ae9f-0c4de5252e04", "00", "Aprobada"
            ),
            TransactionVO(
                "4", "000123", "000ABC", "1000",
                "1234567890123456", "2cbfcb81-2692-4a98-8487-e42e3d3ae127",
                "402bc4ca-b820-4777-accc-2e4bee8d86e6", "00", "Aprobada"
            )

        )
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
//        val annulment = AnnulRequestDTO(receiptId, rrn)
//        val retrofit = serviceRest.getClientWithAuthHeader(
//            Constants.URL,
//            commerceCode,
//            terminalCode)
//        val apiClient = retrofit?.create(ITransactionsServices::class.java)
//        val response = apiClient?.sendAnnulation(annulment)
//        if (response !is AnnulResponseDTO) throw Exception()
        database.getAuthorizationDao().updateAuthorizationStatus(
            receiptId, "99", "Denegada"
        )
        database.getAnnulmentDao().insertAnnulment(annulmentEntity)
    }
}