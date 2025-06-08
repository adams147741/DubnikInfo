package com.example.dubnikinfo.data.local.news

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dubnikinfo.utils.parseDateToTimestamp

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = false)
    val link: String,
    val title: String,
    val date: String,
    val dateStamp: Long?,
    val description: String,
)

/**
 * Converts a NewsEntity to a NewsLine
 */
fun NewsEntity.toNewsLine(): NewsLine {
    return NewsLine(title = title, date = date, description = description, link = link, dateStamp = parseDateToTimestamp(date))
}

/**
 * Converts a NewsLine to a NewsEntity
 */
fun NewsLine.toEntity(): NewsEntity {
    return NewsEntity(title = title, date = date, description = description, link = link, dateStamp = parseDateToTimestamp(date))
}