package com.example.dubnikinfo.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun parseDateToTimestamp(dateString: String): Long? {
    val format = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
    return try {
        format.parse(dateString)?.time
    } catch (e: Exception) {
        null
    }
}

fun parseLongToTimeStamp(timestamp: Long): com.google.firebase.Timestamp {
    return com.google.firebase.Timestamp(timestamp, 0)
}

fun formatTimestampToString(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    val dateTime = Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
    return formatter.format(dateTime)
}

fun formatLocalDateToString(localDate: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return formatter.format(localDate)
}

fun formatTimestampToLocalDate(timestamp: com.google.firebase.Timestamp): LocalDate {
    val thing = Instant.ofEpochMilli(timestamp.seconds).atZone(ZoneId.systemDefault()).toLocalDate()
    return timestamp.toDate()
        .toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}