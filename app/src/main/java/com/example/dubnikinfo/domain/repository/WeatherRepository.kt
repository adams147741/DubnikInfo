package com.example.dubnikinfo.domain.repository

import android.util.Log
import com.example.dubnikinfo.data.remote.WeatherApi

interface WeatherRepository {
    suspend fun getLocalTemperature(lat: Double, lon: Double): Double?
    suspend fun updateTemperature(lat: Double, lon: Double)
}

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
) : WeatherRepository {

    private var latestTemperature: Double? = null

    override suspend fun getLocalTemperature(lat: Double, lon: Double): Double? {
        if (latestTemperature != null) {
            return latestTemperature
        }
        return try {
            val temp = weatherApi.getCurrentWeather(lat, lon).current_weather.temperature
            latestTemperature = temp
            return temp
        }
        catch (e: Exception) {
            Log.d("WeatherRepository", "getLocalTemperature: $e")
            null
        }
    }

    override suspend fun updateTemperature(lat: Double, lon: Double) {
        val temp = weatherApi.getCurrentWeather(lat, lon).current_weather.temperature
    }
}