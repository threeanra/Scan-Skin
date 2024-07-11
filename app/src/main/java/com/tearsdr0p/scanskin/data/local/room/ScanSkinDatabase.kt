package com.tearsdr0p.scanskin.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1)
abstract class ScanSkinDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: ScanSkinDatabase? = null

        fun getDatabase(context: Context): ScanSkinDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScanSkinDatabase::class.java,
                    "scanskin_database")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}