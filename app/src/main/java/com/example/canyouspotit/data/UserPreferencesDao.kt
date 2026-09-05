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

    // every row (in practice just the one) - used by the data export, which serialises
    // all three tables uniformly as lists
    @Query("SELECT * FROM user_preferences")
    suspend fun getAll(): List<UserPreferences>

    // wipes the stored preferences row - used when a user turns data saving off and opts
    // to delete what was already saved
    @Query("DELETE FROM user_preferences")
    suspend fun deleteAll()
}
