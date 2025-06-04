package com.example.dubnikinfo.data

import android.content.Context
import com.example.dubnikinfo.data.local.AppDatabase
import com.example.dubnikinfo.domain.repository.NewsRepository
import com.example.dubnikinfo.domain.repository.RssDataSource
import androidx.room.Room
import com.example.dubnikinfo.data.local.thrash.ThrashDao
import com.example.dubnikinfo.domain.repository.NewsRepositoryImpl
import com.example.dubnikinfo.domain.repository.ThrashRepository
import com.example.dubnikinfo.domain.repository.ThrashRepositoryImpl

interface AppContainer {
    val newsRepository: NewsRepository
    val thrashRepository: ThrashRepository
}

class DefaultAppContainer(context: Context) : AppContainer {
    private val database = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "app_database"
    ).fallbackToDestructiveMigration(true).build()

    override val newsRepository: NewsRepository =
        NewsRepositoryImpl(
            RssDataSource(),
            database.newsDao()
        )

    override val thrashRepository: ThrashRepository =
        ThrashRepositoryImpl(
            database.thrashDao()
        )
}