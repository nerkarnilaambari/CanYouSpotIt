package com.example.canyouspotit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// One row = one practice question attempt, so we can track progress and accuracy over time.
@Entity(tableName = "learning_sessions")
data class LearningSession(
    // Room fills this in automatically, that's why it defaults to 0.
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    // which practice example this attempt was for
    val exampleId: Int,
    val userAnswer: String,
    val isCorrect: Boolean,
    // used to break down accuracy stats by difficulty
    val difficultyLevel: Int,
    val timestamp: Long,
    // ms between the card being shown and the swipe being registered
    val decisionTimeMs: Long = 0
)
