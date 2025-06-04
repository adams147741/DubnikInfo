package com.example.dubnikinfo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dubnikinfo.data.local.news.NewsDao
import com.example.dubnikinfo.data.local.news.NewsEntity
import com.example.dubnikinfo.data.local.thrash.ThrashDao
import com.example.dubnikinfo.data.local.thrash.ThrashEntity

@Database(
    entities = [NewsEntity::class, ThrashEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun thrashDao(): ThrashDao
}