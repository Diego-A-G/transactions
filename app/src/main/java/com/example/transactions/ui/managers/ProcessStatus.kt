package com.example.transactions.ui.managers


sealed class ProcessStatus<out T : Any?> {
    data class Loading(val message: String) : ProcessStatus<Nothing>()
    data class Error(val message: String, val throwable: Throwable?) : ProcessStatus<Nothing>()
    data class Success<out T : Any>(val data: T) : ProcessStatus<T>()
}