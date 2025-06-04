package com.example.dubnikinfo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = false)
    val link: String,
    val title: String,
    val date: String,
    val description: String,
)

fun NewsEntity.toNewsLine(): NewsLine {
    return NewsLine(title = title, date = date, description = description, link = link)
}

fun NewsLine.toEntity(): NewsEntity {
    return NewsEntity(title = title, date = date, description = description, link = link)
}