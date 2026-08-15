package com.example.canyouspotit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Only ever one row here (one user's settings), that's why id is hardcoded to 1
// instead of using autoGenerate like the other tables.
@Entity(tableName = "user_preferences")
data class UserPreferences(
    @PrimaryKey val id: Int = 1,
    val consentGiven: Boolean,
    // used for stuff like localized content
    val detectedRegion: String,
    val useDarkMode: Boolean
)
