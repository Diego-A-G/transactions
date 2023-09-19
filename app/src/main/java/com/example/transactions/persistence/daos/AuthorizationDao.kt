package com.example.transactions.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.transactions.persistence.entities.AuthorizationEntity

@Dao
interface AuthorizationDao {
    @Insert
    suspend fun insertAuthorization(authorization: AuthorizationEntity)

    @Query("SELECT * FROM authorization WHERE id = :authorizationId")
    suspend fun getAuthorizationById(authorizationId: String): AuthorizationEntity?

    @Query("SELECT * FROM authorization")
    suspend fun getAllAuthorizations(): List<AuthorizationEntity>

    @Query("UPDATE authorization SET statusCode = :newStatusCode, statusDescription = :newStatusDescription WHERE receiptId = :receiptId")
    suspend fun updateAuthorizationStatus(
        receiptId: String,
        newStatusCode: String,
        newStatusDescription: String
    )

}
