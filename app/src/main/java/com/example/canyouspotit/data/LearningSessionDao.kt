package com.example.canyouspotit.data

import androidx.room.*

@Dao
interface LearningSessionDao {
    @Insert
    suspend fun insert(session: LearningSession)

    @Query("SELECT * FROM learning_sessions")
    suspend fun getAll(): List<LearningSession>

    @Query("SELECT COUNT(*) FROM learning_sessions WHERE isCorrect = 1")
    suspend fun getCorrectCount(): Int

    @Query("SELECT COUNT(*) FROM learning_sessions")
    suspend fun getTotalCount(): Int

    @Query("SELECT COUNT(*) FROM learning_sessions WHERE isCorrect = 1 AND difficultyLevel = :level")
    suspend fun getCorrectCountForLevel(level: Int): Int

    @Query("SELECT COUNT(*) FROM learning_sessions WHERE difficultyLevel = :level")
    suspend fun getTotalCountForLevel(level: Int): Int
}
