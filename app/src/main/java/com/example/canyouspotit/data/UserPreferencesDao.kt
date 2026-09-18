package com.example.canyouspotit.data

import androidx.room.*

@Dao
interface UserPreferencesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prefs: UserPreferences)

    // Unused.
    @Update
    suspend fun update(prefs: UserPreferences)

    // Unused.
    @Query("SELECT * FROM user_preferences WHERE id = 1")
    suspend fun get(): UserPreferences?

    @Query("SELECT * FROM user_preferences")
    suspend fun getAll(): List<UserPreferences>

    // Used when the user turns data saving off and opts to delete what was already saved.
    @Query("DELETE FROM user_preferences")
    suspend fun deleteAll()
}
