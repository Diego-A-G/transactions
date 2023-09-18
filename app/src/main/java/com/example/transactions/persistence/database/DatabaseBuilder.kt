package com.example.transactions.persistence.database

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    private var INSTANCE: CrediBankDatabase? = null

    fun getInstance(context: Context): CrediBankDatabase {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                CrediBankDatabase::class.java, "credi-bank-database"
            ).build()
        }
        return INSTANCE!!
    }
}
