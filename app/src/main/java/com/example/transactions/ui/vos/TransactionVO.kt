package com.example.transactions.ui.vos

data class TransactionVO(
    val id: String,
    val commerceCode: String,
    val terminalCode: String,
    val amount: String,
    val card: String,

    val receiptId: String? = "",
    val rrn: String? = "",
    val statusCode: String? = "",
    val statusDescription: String? = ""
)
