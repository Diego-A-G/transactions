package com.example.transactions.repository.implementation

import com.example.transactions.repository.declaration.IUserRepository
import com.example.transactions.ui.vos.UserVO

class UserRepository : IUserRepository {
    override fun getUserByCredentianls(user: String, pass: String) {
        TODO("Not yet implemented")
    }

    override fun createUser(user: UserVO) {
        TODO("Not yet implemented")
    }
}