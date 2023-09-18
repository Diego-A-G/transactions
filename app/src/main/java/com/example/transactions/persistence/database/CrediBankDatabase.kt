package com.example.transactions.persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.transactions.persistence.daos.AnnulmentDao
import com.example.transactions.persistence.daos.AuthorizationDao
import com.example.transactions.persistence.entities.AnnulmentEntity
import com.example.transactions.persistence.entities.AuthorizationEntity

@Database(
    entities = [AuthorizationEntity::class, AnnulmentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CrediBankDatabase : RoomDatabase() {

    abstract fun getAuthorizationDao(): AuthorizationDao
    abstract fun getAnnulmentDao(): AnnulmentDao


}