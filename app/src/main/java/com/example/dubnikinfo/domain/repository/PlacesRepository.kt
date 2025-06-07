package com.example.dubnikinfo.domain.repository

import com.example.dubnikinfo.data.local.place.Place
import com.example.dubnikinfo.data.remote.FirebasePlaces

interface PlacesRepository {
    suspend fun getPlaces(): List<Place>
}

class PlacesRepositoryImpl(
    private val firebasePlaces: FirebasePlaces
) : PlacesRepository {
    override suspend fun getPlaces(): List<Place> {
        val places = firebasePlaces.getPlaces()
        return places
    }
}