package com.example.dubnikinfo.data.remote

import android.util.Log
import com.example.dubnikinfo.data.local.place.Place
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class FirebasePlaces {
    private val databes = FirebaseFirestore.getInstance()
    private val collection = databes.collection("places")

    suspend fun getPlaces(): List<Place> {
        return try {
            collection.get().await().documents.mapNotNull { document ->
                document.toObject(Place::class.java)
            }
        } catch (e: Exception) {
            Log.d("FirebasePlaces", "Error getting places: ${e.message}")
            emptyList()
        }
    }
}