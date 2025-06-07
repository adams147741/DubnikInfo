package com.example.dubnikinfo.data.remote

import android.util.Log
import com.example.dubnikinfo.data.local.trash.TrashPickup
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseTrash {
    private val database = FirebaseFirestore.getInstance()
    private val collection = database.collection("trash_pickups")

    suspend fun getTrashPickups(): List<TrashPickup> {
        return try {
            collection.get().await().documents.mapNotNull { document ->
                document.toObject(TrashPickup::class.java)
            }
        } catch (e: Exception) {
            Log.d("FirebaseTrash", "Error getting trash pickups: ${e.message}")
            emptyList()
        }
    }
}