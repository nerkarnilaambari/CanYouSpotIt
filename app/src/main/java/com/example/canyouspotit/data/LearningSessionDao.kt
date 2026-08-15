package com.example.canyouspotit.data

import androidx.room.*

// DAO for learning_sessions, handles saving practice attempts and pulling accuracy stats.
@Dao
interface LearningSessionDao {
    // saves one practice attempt right after the user answers
    @Insert
    suspend fun insert(session: LearningSession)

    @Query("SELECT * FROM learning_sessions")
    suspend fun getAll(): List<LearningSession>

    // pair this with getTotalCount() to get an accuracy percentage
    @Query("SELECT COUNT(*) FROM learning_sessions WHERE isCorrect = 1")
    suspend fun getCorrectCount(): Int

    @Query("SELECT COUNT(*) FROM learning_sessions")
    suspend fun getTotalCount(): Int

    // same idea as getCorrectCount/getTotalCount, just scoped to one difficulty level
    @Query("SELECT COUNT(*) FROM learning_sessions WHERE isCorrect = 1 AND difficultyLevel = :level")
    suspend fun getCorrectCountForLevel(level: Int): Int

    @Query("SELECT COUNT(*) FROM learning_sessions WHERE difficultyLevel = :level")
    suspend fun getTotalCountForLevel(level: Int): Int
}
