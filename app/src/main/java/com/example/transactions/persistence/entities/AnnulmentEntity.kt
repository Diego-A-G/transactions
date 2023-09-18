package com.example.transactions.persistence.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "annulment")
data class AnnulmentEntity(
    @PrimaryKey val receiptId: String,
    val rrn: String,
    val statusCode: String,
    val statusDescription: String
)

