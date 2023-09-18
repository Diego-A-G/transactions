package com.example.transactions.repository.dto

import com.google.gson.annotations.SerializedName


data class AuthRequestDTO(
    @SerializedName(ID) val id: String,
    @SerializedName(COMERCE_CODE) val commerceCode: String,
    @SerializedName(TERMINAL_CODE) val terminalCode: String,
    @SerializedName(AMOUNT) val amount: String,
    @SerializedName(CARD) val card: String,
) {
    companion object {
        const val ID = "id"
        const val COMERCE_CODE = "commerceCode"
        const val TERMINAL_CODE = "terminalCode"
        const val AMOUNT = "amount"
        const val CARD = "card"
    }
}
