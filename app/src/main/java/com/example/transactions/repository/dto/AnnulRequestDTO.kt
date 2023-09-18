package com.example.transactions.repository.dto

import com.google.gson.annotations.SerializedName

data class AnnulRequestDTO(
    @SerializedName(RECEIPT_ID) val receiptId: String,
    @SerializedName(RRN) val rrn: String
) {
    companion object {
        const val RECEIPT_ID = "receiptId"
        const val RRN = "rrn"
    }
}
