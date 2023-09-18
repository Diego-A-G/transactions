package com.example.transactions.repository.dto

import com.google.gson.annotations.SerializedName

class AuthResponseDTO(
    @SerializedName(RECEIPT_ID) val receiptId: String,
    @SerializedName(RRN) val rrn: String,
    @SerializedName(STATUS_CODE) val statusCode: String,
    @SerializedName(STATUS_DESCRIPTION) val statusDescription: String
) {
    companion object {
        const val RECEIPT_ID = "receiptId"
        const val RRN = "rrn"
        const val STATUS_CODE = "statusCode"
        const val STATUS_DESCRIPTION = "statusDescription"
    }
}