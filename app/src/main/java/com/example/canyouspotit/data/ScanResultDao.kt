package com.example.canyouspotit.data

import androidx.room.*

// Room generates the SQL from these annotations. All suspend - no DB work on the main thread.
@Dao
interface ScanResultDao {
    // Returns the new row's id.
    @Insert
    suspend fun insert(scanResult: ScanResult): Long

    @Update
    suspend fun update(scanResult: ScanResult)

    // Targeted UPDATE of just the note column.
    @Query("UPDATE scan_results SET emotionalResponseNote = :note WHERE id = :id")
    suspend fun updateNote(id: Int, note: String?)

    @Query("SELECT * FROM scan_results ORDER BY timestamp DESC")
    suspend fun getAll(): List<ScanResult>

    @Query("SELECT * FROM scan_results WHERE id = :id")
    suspend fun getById(id: Int): ScanResult?

    // Stats helpers - unused so far.
    @Query("SELECT COUNT(*) FROM scan_results")
    suspend fun getCount(): Int

    @Query("SELECT verdict FROM scan_results GROUP BY verdict ORDER BY COUNT(*) DESC LIMIT 1")
    suspend fun getMostCommonVerdict(): String?

    @Query("SELECT emotionalResponse FROM scan_results WHERE emotionalResponse IS NOT NULL GROUP BY emotionalResponse ORDER BY COUNT(*) DESC LIMIT 1")
    suspend fun getMostCommonEmotion(): String?

    // Used when the user turns data saving off and opts to delete what was already saved.
    @Query("DELETE FROM scan_results")
    suspend fun deleteAll()
}
