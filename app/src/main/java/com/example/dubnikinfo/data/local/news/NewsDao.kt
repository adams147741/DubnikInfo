package com.example.dubnikinfo.data.local.news

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {
    /**
     * Returns all news from the database
     */
    @Query("SELECT * FROM news")
    suspend fun getAll(): List<NewsEntity>

    /**
     * Returns the first news from the database
     */
    @Query("SELECT * FROM news LIMIT 1")
    suspend fun getFirst(): NewsEntity?

    /**
     * Inserts a list of news into the database
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(news: List<NewsEntity>)

    /**
     * Deletes all news from the database
     */
    @Query("DELETE FROM news")
    suspend fun deleteAll()
}
