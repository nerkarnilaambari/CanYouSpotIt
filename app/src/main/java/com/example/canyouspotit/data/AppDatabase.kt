package com.example.canyouspotit.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Room reads this class and generates the actual database + DAO implementations for us.
@Database(
    entities = [ScanResult::class, LearningSession::class, UserPreferences::class],
    // bump this whenever a table's structure changes, and add a migration for it
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // no bodies needed, Room writes the implementations
    abstract fun scanResultDao(): ScanResultDao
    abstract fun learningSessionDao(): LearningSessionDao
    abstract fun userPreferencesDao(): UserPreferencesDao

    companion object {
        // @Volatile so other threads immediately see it once it's set
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // go through this instead of building AppDatabase directly, keeps us to one instance
        fun getDatabase(context: Context): AppDatabase {
            // synchronized so two threads can't both build it at the same time
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "canyouspotit_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
