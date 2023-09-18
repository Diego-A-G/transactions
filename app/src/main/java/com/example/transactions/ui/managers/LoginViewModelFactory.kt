package com.example.transactions.ui.managers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.transactions.repository.declaration.IUserRepository
import com.example.transactions.repository.implementation.UserRepository

class LoginViewModelFactory(
    private val userRepository: IUserRepository
) : ViewModelProvider.Factory {

    constructor() : this(
        UserRepository()
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java))
            return LoginViewModel(userRepository) as T
        throw IllegalArgumentException("unknownm vm class")
    }
}