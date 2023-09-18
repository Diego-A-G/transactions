package com.example.transactions.ui.managers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LaunchViewModel : ViewModel() {

    private val launchStatus =
        MutableLiveData<ELaunchStatus>().apply { value = ELaunchStatus.OPENING }

    fun launchStatus(): LiveData<ELaunchStatus> = launchStatus

    fun confirmStep(confirmedStep: ELaunchStatus) {
        when (confirmedStep) {
            ELaunchStatus.OPENING -> launchStatus.postValue(ELaunchStatus.CONFIG)
            ELaunchStatus.CONFIG -> launchStatus.postValue(ELaunchStatus.LOGIN)
            ELaunchStatus.LOGIN -> launchStatus.postValue(ELaunchStatus.MENU)
            ELaunchStatus.MENU -> launchStatus.postValue(ELaunchStatus.AUTH)
            ELaunchStatus.AUTH -> launchStatus.postValue(ELaunchStatus.VIEW)
            ELaunchStatus.VIEW -> launchStatus.postValue(ELaunchStatus.SEARCH)
            else -> {}
        }
    }

    enum class ELaunchStatus {
        OPENING,
        CONFIG,
        LOGIN,
        MENU,
        AUTH,
        SEARCH,
        VIEW
    }
}