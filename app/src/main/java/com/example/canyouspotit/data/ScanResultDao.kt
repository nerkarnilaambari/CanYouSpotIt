package com.example.canyouspotit.data

import androidx.room.*

@Dao
interface ScanResultDao {
    @Insert
    suspend fun insert(scanResult: ScanResult): Long

    @Update
    suspend fun update(scanResult: ScanResult)

    @Query("SELECT * FROM scan_results ORDER BY timestamp DESC")
    suspend fun getAll(): List<ScanResult>

    @Query("SELECT * FROM scan_results WHERE id = :id")
    suspend fun getById(id: Int): ScanResult?

    @Query("SELECT COUNT(*) FROM scan_results")
    suspend fun getCount(): Int

    @Query("SELECT verdict FROM scan_results GROUP BY verdict ORDER BY COUNT(*) DESC LIMIT 1")
    suspend fun getMostCommonVerdict(): String?

    @Query("SELECT emotionalResponse FROM scan_results WHERE emotionalResponse IS NOT NULL GROUP BY emotionalResponse ORDER BY COUNT(*) DESC LIMIT 1")
    suspend fun getMostCommonEmotion(): String?
}
