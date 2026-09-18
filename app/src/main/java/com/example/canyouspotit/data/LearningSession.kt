package com.example.canyouspotit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// One row = one practice-card attempt. Backs difficulty progression and the category recap.
@Entity(tableName = "learning_sessions")
data class LearningSession(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val exampleId: Int,        // a ScamExample.id in the static scamExamples list
    val userAnswer: String,    // "Legitimate" or "Scam"
    val isCorrect: Boolean,
    val difficultyLevel: Int,
    val timestamp: Long,
    val decisionTimeMs: Long = 0   // card shown -> swipe registered; feeds the "too fast" nudge
)
