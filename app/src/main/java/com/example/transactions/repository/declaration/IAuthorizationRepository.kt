package com.example.transactions.repository.declaration

import com.example.transactions.ui.vos.TransactionVO

interface IAuthorizationRepository {

    suspend fun authTransaction(transactionVO: TransactionVO)
    suspend fun saveTransaction(transactionVO: TransactionVO)
    suspend fun getTransactions(): List<TransactionVO>
    suspend fun getTransactionByReceipt(receipt: String): TransactionVO
    suspend fun getAuthTransactions(): List<TransactionVO>
    suspend fun cancelTransaction(receiptId: String, rrn: String)
}