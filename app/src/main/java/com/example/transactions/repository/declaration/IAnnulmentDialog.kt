package com.example.transactions.repository.declaration

interface IAnnulmentDialog {

    fun onAnnulmentTransaction(
        receiptId: String,
        rrn: String,
        commerceCode: String,
        terminalCode: String
    )

}