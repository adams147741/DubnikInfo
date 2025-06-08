package com.example.dubnikinfo.data

import android.content.Context
import com.example.dubnikinfo.data.local.AppDatabase
import com.example.dubnikinfo.domain.repository.NewsRepository
import com.example.dubnikinfo.data.remote.RssDataSource
import androidx.room.Room
import com.example.dubnikinfo.data.remote.FirebasePlaces
import com.example.dubnikinfo.data.remote.FirebaseTrash
import com.example.dubnikinfo.data.remote.WeatherApi
import com.example.dubnikinfo.domain.repository.NewsRepositoryImpl
import com.example.dubnikinfo.domain.repository.TrashRepository
import com.example.dubnikinfo.domain.repository.TrashRepositoryImpl
import com.example.dubnikinfo.domain.repository.WeatherRepository
import com.example.dubnikinfo.domain.repository.WeatherRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.dubnikinfo.domain.repository.PlacesRepository
import com.example.dubnikinfo.domain.repository.PlacesRepositoryImpl

interface AppContainer {
    val newsRepository: NewsRepository
    val trashRepository: TrashRepository
    val weatherRepository: WeatherRepository
    val placesRepository: PlacesRepository
}

/**
 * Default implementation of the AppContainer interface
 * @param context Context - the application context
 * @return DefaultAppContainer - the default app container
 * @return NewsRepository - the news repository
 * @return TrashRepository - the trash repository
 * @return WeatherRepository - the weather repository
 * @return PlacesRepository - the places repository
 */
class DefaultAppContainer(context: Context) : AppContainer {
    private val database = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "app_database"
    ).fallbackToDestructiveMigration(true).build()

    /**
     * Returns the news repository
     */
    override val newsRepository: NewsRepository =
        NewsRepositoryImpl(
            RssDataSource(),
            database.newsDao()
        )

    /**
     * Returns the trash repository
     */
    override val trashRepository: TrashRepository =
        TrashRepositoryImpl(
            database.thrashDao(),
            FirebaseTrash()
        )

    /**
     * Returns the weather repository and initiates it with the Open-Meteo API
     */
    private val weatherApi: WeatherApi = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)

    /**
     * Returns the weather repository
     */
    override val weatherRepository: WeatherRepository =
        WeatherRepositoryImpl(
            weatherApi
        )

    /**
     * Returns the places repository
     */
    override val placesRepository: PlacesRepository =
        PlacesRepositoryImpl(
            FirebasePlaces()
        )
}