package com.example.dubnikinfo.data.local.trash

import androidx.room.Entity

/**
 * Data class representing a trash
 */
@Entity
    (tableName = "thrash", primaryKeys = ["date", "type"])
data class TrashEntity(
    val date: Long,
    val type: Int
)