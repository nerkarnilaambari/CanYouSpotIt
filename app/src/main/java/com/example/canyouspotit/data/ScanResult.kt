package com.example.canyouspotit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// One row = one scan the user ran. We keep these for history and for stats
// like "what's the most common verdict you've gotten".
@Entity(tableName = "scan_results")
data class ScanResult(
    // Room fills this in automatically, that's why it defaults to 0.
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val messageText: String,
    // e.g. "safe" or "scam"
    val verdict: String,
    val timestamp: Long,
    // nullable since not every scan has this filled in
    val emotionalResponse: String? = null
)
