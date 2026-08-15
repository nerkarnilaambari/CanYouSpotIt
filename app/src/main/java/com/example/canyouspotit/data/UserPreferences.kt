package com.example.canyouspotit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_preferences")
data class UserPreferences(
    @PrimaryKey val id: Int = 1,
    val consentGiven: Boolean,
    val detectedRegion: String,
    val useDarkMode: Boolean
)
