package com.example.dubnikinfo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dubnikinfo.data.local.news.NewsDao
import com.example.dubnikinfo.data.local.news.NewsEntity
import com.example.dubnikinfo.data.local.trash.TrashDao
import com.example.dubnikinfo.data.local.trash.TrashEntity

@Database(
    entities = [NewsEntity::class, TrashEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun thrashDao(): TrashDao
}