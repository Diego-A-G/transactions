package com.example.transactions.persistence.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authorization")
data class AuthorizationEntity(
    @PrimaryKey val id: String,
    val commerceCode: String,
    val terminalCode: String,
    val amount: String,
    val card: String,
    val receiptId: String,
    val rrn: String,
    val statusCode: String,
    val statusDescription: String
)

