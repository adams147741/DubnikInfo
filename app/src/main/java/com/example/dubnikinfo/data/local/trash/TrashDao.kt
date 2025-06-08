package com.example.dubnikinfo.data.local.trash

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrashDao {
    /**
     * Returns all trash from the database
     */
    @Query("SELECT * FROM thrash")
    suspend fun getTrash(): List<TrashEntity>

    /**
     * Returns all trash from the database for a specific date
     */
    @Query("SELECT * FROM thrash WHERE date = :date")
    suspend fun getTrashByDate(date: Long): List<TrashEntity>

    /**
     * Inserts a trash into the database
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrash(thrash: TrashEntity)

    /**
     * Returns the closest trash from the database for a specific date
     */
    @Query("SELECT * FROM thrash WHERE date >= :date ORDER BY date ASC LIMIT 1")
    suspend fun getClosestTrashDate(date: Long): TrashEntity?

    /**
     * Deletes all trash from the database
     */
    @Query("DELETE FROM thrash")
    suspend fun deleteAllTrash()
}