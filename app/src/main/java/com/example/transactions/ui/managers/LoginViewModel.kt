package com.example.transactions.ui.managers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.transactions.repository.declaration.IUserRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: IUserRepository
) : ViewModel() {

    private var scope = CoroutineScope(Dispatchers.IO)

    private val isLogged = MutableLiveData(false)
    fun isLogged(): LiveData<Boolean> = isLogged

    private val processStatus = MutableLiveData<ProcessStatus<Boolean>>()
    fun getProcessStatus(): LiveData<ProcessStatus<Boolean>> = processStatus

    private val handler = CoroutineExceptionHandler { _, ex ->
        processStatus.postValue(ProcessStatus.Error(ex.message ?: "", ex))
    }

    fun validateLogin(user: String, encryptedPassword: String) {
        scope.launch(handler) {
            processStatus.postValue(ProcessStatus.Loading("loading user"))
            delay(2000)
            isLogged.postValue(true)
            processStatus.postValue(ProcessStatus.Success(true))
        }
    }

}