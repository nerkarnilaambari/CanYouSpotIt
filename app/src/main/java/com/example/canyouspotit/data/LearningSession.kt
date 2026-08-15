package com.example.canyouspotit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "learning_sessions")
data class LearningSession(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val exampleId: Int,
    val userAnswer: String,
    val isCorrect: Boolean,
    val difficultyLevel: Int,
    val timestamp: Long
)
