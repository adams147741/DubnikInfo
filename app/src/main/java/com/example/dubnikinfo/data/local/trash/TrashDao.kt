package com.example.dubnikinfo.data.local.trash

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrashDao {
    @Query("SELECT * FROM thrash")
    suspend fun getTrash(): List<TrashEntity>

    @Query("SELECT * FROM thrash WHERE date = :date")
    suspend fun getTrashByDate(date: Long): List<TrashEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrash(thrash: TrashEntity)

    @Query("SELECT * FROM thrash WHERE date >= :date ORDER BY date ASC LIMIT 1")
    suspend fun getClosestTrashDate(date: Long): TrashEntity?

    @Query("DELETE FROM thrash")
    suspend fun deleteAllTrash()
}