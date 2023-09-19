package com.example.transactions.repository.declaration

import com.example.transactions.ui.vos.TransactionVO

interface ISearchDialog {

    fun searchTransaction(id: String): TransactionVO?

}