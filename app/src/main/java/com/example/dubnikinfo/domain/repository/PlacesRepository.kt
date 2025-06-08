package com.example.dubnikinfo.domain.repository

import com.example.dubnikinfo.data.local.place.Place
import com.example.dubnikinfo.data.remote.FirebasePlaces

interface PlacesRepository {
    suspend fun getPlaces(): List<Place>
}

class PlacesRepositoryImpl(
    private val firebasePlaces: FirebasePlaces
) : PlacesRepository {
    /**
     * Returns a list of interesting places from firebase
     * @return List<Place> - a list of interesting places
     */
    override suspend fun getPlaces(): List<Place> {
        val places = firebasePlaces.getPlaces()
        return places
    }
}