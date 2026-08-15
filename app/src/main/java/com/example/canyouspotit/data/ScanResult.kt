package com.example.canyouspotit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scan_results")
data class ScanResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val messageText: String,
    val verdict: String,
    val timestamp: Long,
    val emotionalResponse: String? = null
)
