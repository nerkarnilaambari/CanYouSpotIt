package com.example.canyouspotit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Always one row (id 1). Only the data export reads it back.
@Entity(tableName = "user_preferences")
data class UserPreferences(
    @PrimaryKey val id: Int = 1,
    val consentGiven: Boolean,
    val detectedRegion: String   // "IN" / "DE" / "GLOBAL"
)
