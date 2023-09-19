package com.example.transactions.ui.vos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransactionVO(
    val id: String,
    val commerceCode: String,
    val terminalCode: String,
    val amount: String,
    val card: String,
    //TODO:objeto aparte
    var receiptId: String? = "",
    var rrn: String? = "",
    var statusCode: String? = "",
    var statusDescription: String? = ""
) : Parcelable
