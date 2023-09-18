package com.example.transactions.repository.declaration

import com.example.transactions.ui.vos.UserVO

interface IUserRepository {

    fun getUserByCredentianls(user: String, pass: String)
    fun createUser(user: UserVO)

}
