package com.example.dubnikinfo.data.local.thrash

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ThrashDao {
    @Query("SELECT * FROM thrash")
    suspend fun getThrash(): List<ThrashEntity>

    @Query("SELECT * FROM thrash WHERE date = :date")
    suspend fun getThrashByDate(date: Long): List<ThrashEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThrash(thrash: ThrashEntity)

}