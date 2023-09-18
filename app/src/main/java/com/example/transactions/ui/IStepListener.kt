package com.example.transactions.ui

interface IStepListener {

    fun onConfigurationCompleted()

    fun onLoginCompleted()

    fun toAuthTransaction()

    fun toAuthCancelled()

    fun toSearchTransactions()

    fun toSearchCanceled()

    fun toViewTransaction()

    fun toViewCanceled()

}