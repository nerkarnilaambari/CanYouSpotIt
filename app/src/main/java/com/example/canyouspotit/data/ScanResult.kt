package com.example.canyouspotit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// One row = one scan the user ran. Kept for history and verdict/emotion stats.
@Entity(tableName = "scan_results")
data class ScanResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val messageText: String,
    val verdict: String,   // "SAFE" / "CAUTION" / "SCAM"
    val timestamp: Long,
    // null if the user skipped the emotion step
    val emotionalResponse: String? = null,
    // "URGENCY" / "REWARD" / "THREAT" - the dominant tactic among the scan's flags.
    // Always null for SAFE.
    val primaryTactic: String? = null,
    // Optional free-text note from the emotional-response screen, alongside the emotion.
    // Null when left blank / skipped.
    val emotionalResponseNote: String? = null
)
