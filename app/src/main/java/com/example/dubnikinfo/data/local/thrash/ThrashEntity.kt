package com.example.dubnikinfo.data.local.thrash

import androidx.annotation.IntegerRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
    (tableName = "thrash", primaryKeys = ["date", "type"])
data class ThrashEntity(
    val date: Long,
    val type: Int
)