package com.example.transactions.ui.managers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.transactions.repository.declaration.IAuthorizationRepository
import com.example.transactions.ui.vos.TransactionVO
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionViewModel(
    private val transactionRepo: IAuthorizationRepository
) : ViewModel() {

    private var scope = CoroutineScope(Dispatchers.Main)

    private val transactions = MutableLiveData<List<TransactionVO>>()

    fun getTransactions(): LiveData<List<TransactionVO>> = transactions

    private val processStatus = MutableLiveData<ProcessStatus<String>>()
    fun getProcessStatus(): LiveData<ProcessStatus<String>> = processStatus

    private val handler = CoroutineExceptionHandler { _, ex ->
        processStatus.postValue(ProcessStatus.Error(ex.message ?: "", ex))
    }

    fun sendAuthorization(transactionInfo: TransactionVO) {
        scope.launch(handler) {
            processStatus.postValue(ProcessStatus.Loading("sending auth"))
            transactionRepo.authTransaction(transactionInfo)
            processStatus.postValue(ProcessStatus.Success("sendt auth"))
        }
    }

    fun getTransactionByReceipt(id: String) {
        scope.launch(handler) {
            processStatus.postValue(ProcessStatus.Loading("searching transaction"))
            transactionRepo.getTransactionByReceipt(id)
            processStatus.postValue(ProcessStatus.Success("success"))
        }
    }

    fun fetchTransactions() {
        scope.launch(handler) {
            withContext(Dispatchers.Default) {
                processStatus.postValue(ProcessStatus.Loading("filling transactions"))
                transactions.postValue(transactionRepo.getTransactions())
                processStatus.postValue(ProcessStatus.Success("success"))
            }
        }
    }

    fun annulTransaction(
        receiptId: String,
        rrn: String,
        commerceCode: String,
        terminalCode: String
    ) {
        scope.launch(handler) {
            processStatus.postValue(ProcessStatus.Loading("loading annulment"))
            transactionRepo.cancelTransaction(receiptId, rrn, commerceCode, terminalCode)
            processStatus.postValue(ProcessStatus.Success("success"))
        }
    }

}