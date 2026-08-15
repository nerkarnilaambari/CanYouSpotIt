package com.example.canyouspotit.data

import androidx.room.*

// DAO for the one-row user_preferences table (consent, region, theme settings).
@Dao
interface UserPreferencesDao {
    // REPLACE means saving again just overwrites the existing row instead of erroring,
    // since id is always 1 here
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prefs: UserPreferences)

    @Update
    suspend fun update(prefs: UserPreferences)

    // grabbed on app startup to load whatever the user had set
    @Query("SELECT * FROM user_preferences WHERE id = 1")
    suspend fun get(): UserPreferences?
}
