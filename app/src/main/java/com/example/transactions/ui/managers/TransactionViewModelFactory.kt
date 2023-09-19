package com.example.transactions.ui.managers

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.transactions.persistence.database.DatabaseBuilder
import com.example.transactions.repository.declaration.IAuthorizationRepository
import com.example.transactions.repository.implementation.AuthorizationRepository
import com.example.transactions.services.ServiceRest

class TransactionViewModelFactory(
    private val transactionRepo: IAuthorizationRepository
) : ViewModelProvider.Factory {

    constructor(context: Context) : this(
        AuthorizationRepository(
            ServiceRest(),
            DatabaseBuilder.getInstance(context)
        )
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java))
            return TransactionViewModel(transactionRepo) as T
        throw IllegalArgumentException("unknownm vm class")
    }
}