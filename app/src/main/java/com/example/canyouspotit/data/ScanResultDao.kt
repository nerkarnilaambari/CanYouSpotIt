package com.example.canyouspotit.data

import androidx.room.*

// DAO = the interface Room uses to generate our actual SQL calls, so we don't write them by hand.
// Everything's a suspend fun since DB calls shouldn't run on the main thread.
@Dao
interface ScanResultDao {
    // called after a scan finishes, saves the result and hands back the new row's id
    @Insert
    suspend fun insert(scanResult: ScanResult): Long

    // for when a saved scan changes later, e.g. user adds an emotional response
    @Update
    suspend fun update(scanResult: ScanResult)

    // full scan history, newest first
    @Query("SELECT * FROM scan_results ORDER BY timestamp DESC")
    suspend fun getAll(): List<ScanResult>

    // pulls up one specific past scan
    @Query("SELECT * FROM scan_results WHERE id = :id")
    suspend fun getById(id: Int): ScanResult?

    @Query("SELECT COUNT(*) FROM scan_results")
    suspend fun getCount(): Int

    // GROUP BY + ORDER BY COUNT DESC LIMIT 1 = "whichever verdict shows up most"
    @Query("SELECT verdict FROM scan_results GROUP BY verdict ORDER BY COUNT(*) DESC LIMIT 1")
    suspend fun getMostCommonVerdict(): String?

    // same trick as above, just skips scans that never got an emotion logged
    @Query("SELECT emotionalResponse FROM scan_results WHERE emotionalResponse IS NOT NULL GROUP BY emotionalResponse ORDER BY COUNT(*) DESC LIMIT 1")
    suspend fun getMostCommonEmotion(): String?
}
