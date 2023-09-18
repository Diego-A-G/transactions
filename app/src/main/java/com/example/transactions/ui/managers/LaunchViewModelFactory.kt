package com.example.transactions.ui.managers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LaunchViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LaunchViewModel::class.java)) {
            return LaunchViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}