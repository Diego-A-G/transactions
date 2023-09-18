package com.example.transactions.repository.dto

import com.google.gson.annotations.SerializedName

data class AnnulResponseDTO(
    @SerializedName(STATUS_CODE) val statusCode: String,
    @SerializedName(STATUS_DESCRIPTION) val statusDescription: String
) {
    companion object {
        const val STATUS_CODE = "statusCode"
        const val STATUS_DESCRIPTION = "statusDescription"
    }
}
