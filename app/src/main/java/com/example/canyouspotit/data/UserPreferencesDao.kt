package com.example.canyouspotit.data

import androidx.room.*

@Dao
interface UserPreferencesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prefs: UserPreferences)

    @Update
    suspend fun update(prefs: UserPreferences)

    @Query("SELECT * FROM user_preferences WHERE id = 1")
    suspend fun get(): UserPreferences?
}
