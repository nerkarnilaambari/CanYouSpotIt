package com.example.canyouspotit.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Room reads this class and generates the database + DAO implementations.
@Database(
    entities = [ScanResult::class, LearningSession::class, UserPreferences::class],
    // bump this whenever a table's structure changes, and add a migration for it
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // no bodies needed, Room writes the implementations
    abstract fun scanResultDao(): ScanResultDao
    abstract fun learningSessionDao(): LearningSessionDao
    abstract fun userPreferencesDao(): UserPreferencesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // v2 -> v3: adds the nullable emotionalResponseNote column to scan_results; existing
        // rows get NULL.
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "ALTER TABLE `scan_results` ADD COLUMN `emotionalResponseNote` TEXT"
                )
            }
        }

        // v3 -> v4: drops the useDarkMode column from user_preferences. SQLite has no
        // DROP COLUMN here, so rebuild the table without it and copy the rows over.
        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE `user_preferences_new` (" +
                        "`id` INTEGER NOT NULL, " +
                        "`consentGiven` INTEGER NOT NULL, " +
                        "`detectedRegion` TEXT NOT NULL, " +
                        "PRIMARY KEY(`id`))"
                )
                db.execSQL(
                    "INSERT INTO `user_preferences_new` (`id`, `consentGiven`, `detectedRegion`) " +
                        "SELECT `id`, `consentGiven`, `detectedRegion` FROM `user_preferences`"
                )
                db.execSQL("DROP TABLE `user_preferences`")
                db.execSQL("ALTER TABLE `user_preferences_new` RENAME TO `user_preferences`")
            }
        }

        // Single entry point - one instance for the whole app.
        fun getDatabase(context: Context): AppDatabase {
            // synchronized: one builder at a time
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "canyouspotit_database"
                )
                    .addMigrations(MIGRATION_2_3, MIGRATION_3_4)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
