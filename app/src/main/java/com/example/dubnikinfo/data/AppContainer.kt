package com.example.dubnikinfo.data

import android.content.Context
import com.example.dubnikinfo.data.local.AppDatabase
import com.example.dubnikinfo.domain.repository.NewsRepository
import com.example.dubnikinfo.data.remote.RssDataSource
import androidx.room.Room
import com.example.dubnikinfo.data.remote.FirebaseTrash
import com.example.dubnikinfo.data.remote.WeatherApi
import com.example.dubnikinfo.domain.repository.NewsRepositoryImpl
import com.example.dubnikinfo.domain.repository.TrashRepository
import com.example.dubnikinfo.domain.repository.TrashRepositoryImpl
import com.example.dubnikinfo.domain.repository.WeatherRepository
import com.example.dubnikinfo.domain.repository.WeatherRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val newsRepository: NewsRepository
    val trashRepository: TrashRepository
    val weatherRepository: WeatherRepository
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

    override val trashRepository: TrashRepository =
        TrashRepositoryImpl(
            database.thrashDao(),
            FirebaseTrash()
        )

    private val weatherApi: WeatherApi = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)

    override val weatherRepository: WeatherRepository =
        WeatherRepositoryImpl(
            weatherApi
        )

}