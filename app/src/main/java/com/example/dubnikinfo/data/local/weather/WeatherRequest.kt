package com.example.dubnikinfo.data.local.weather

data class WeatherResponse(
    val current_weather: CurrentWeather,
)

data class CurrentWeather(
    val temperature: Double,
)