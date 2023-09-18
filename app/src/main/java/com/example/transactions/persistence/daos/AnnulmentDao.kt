package com.example.transactions.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.transactions.persistence.entities.AnnulmentEntity

@Dao
interface AnnulmentDao {
    @Insert
    suspend fun insertAnnulment(annul: AnnulmentEntity)

    @Query("SELECT * FROM annulment")
    suspend fun getAnnulments(): List<AnnulmentEntity>

}

