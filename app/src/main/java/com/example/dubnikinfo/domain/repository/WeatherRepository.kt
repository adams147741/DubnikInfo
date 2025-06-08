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

    /**
     * Returns current temperature from local cache or from API
     * @param lat Double - latitude of the location
     * @param lon Double - longitude of the location
     * @return Double? - temperature in Celsius
     */
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

    /**
     * Updates current temperature from API into local cache
     */
    override suspend fun updateTemperature(lat: Double, lon: Double) {
        try {
            latestTemperature = weatherApi.getCurrentWeather(lat, lon).current_weather.temperature
        } catch (e: Exception) {
            Log.d("WeatherRepository", "updateTemperature: $e")
        }
    }
}