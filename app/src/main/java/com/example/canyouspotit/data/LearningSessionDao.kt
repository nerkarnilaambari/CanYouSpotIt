package com.example.canyouspotit.data

import androidx.room.*

// Room generates the SQL from these annotations. All suspend - no DB work on the main thread.
@Dao
interface LearningSessionDao {
    @Insert
    suspend fun insert(session: LearningSession)

    @Query("SELECT * FROM learning_sessions")
    suspend fun getAll(): List<LearningSession>

    // Unused.
    @Query("SELECT * FROM learning_sessions WHERE isCorrect = 1")
    suspend fun getAllCorrectSessions(): List<LearningSession>

    // Unused.
    @Query("SELECT COUNT(*) FROM learning_sessions WHERE isCorrect = 1")
    suspend fun getCorrectCount(): Int

    @Query("SELECT COUNT(*) FROM learning_sessions")
    suspend fun getTotalCount(): Int

    // Per-level accuracy - LearnActivity checks this before bumping the difficulty tier.
    @Query("SELECT COUNT(*) FROM learning_sessions WHERE isCorrect = 1 AND difficultyLevel = :level")
    suspend fun getCorrectCountForLevel(level: Int): Int

    @Query("SELECT COUNT(*) FROM learning_sessions WHERE difficultyLevel = :level")
    suspend fun getTotalCountForLevel(level: Int): Int

    // Used when the user turns data saving off and opts to delete what was already saved.
    @Query("DELETE FROM learning_sessions")
    suspend fun deleteAll()
}
