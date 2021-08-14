package com.kolis.test_catalog_app.data.dress.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = DressDatabase.VERSION, entities = [DressInCartEntity::class])
abstract class DressDatabase : RoomDatabase() {

    companion object {

        private var INSTANCE: DressDatabase? = null
        const val VERSION = 2
        private const val DATABASE_NAME = "dress_database"

        fun getInstance(context: Context): DressDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(context.applicationContext, DressDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigrationFrom(1)
                .build()
            return INSTANCE!!
        }
    }

    abstract fun dressInCardDao(): DressInCardDao
}