package com.example.dubnikinfo.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Parses a date string into a timestamp
 * @param dateString String - the date string to be parsed
 * @return Long? - the timestamp
 */
fun parseDateToTimestamp(dateString: String): Long? {
    val format = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
    return try {
        format.parse(dateString)?.time
    } catch (e: Exception) {
        null
    }
}

/**
 * Parses a timestamp into a date string
 * @param timestamp Long - the timestamp to be parsed
 * @return String - the date string
 */
fun parseLongToTimeStamp(timestamp: Long): com.google.firebase.Timestamp {
    return com.google.firebase.Timestamp(timestamp, 0)
}

/**
 * Formats a timestamp into a date string
 * @param timestamp Long - the timestamp to be formatted
 * @return String - the date string
 */
fun formatTimestampToString(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    val dateTime = Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
    return formatter.format(dateTime)
}

/**
 * Formats a local date into a date string
 * @param localDate LocalDate - the local date to be formatted
 * @return String - the date string
 */
fun formatLocalDateToString(localDate: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return formatter.format(localDate)
}

/**
 * Formats a timestamp into a local date
 * @param timestamp com.google.firebase.Timestamp - the timestamp to be formatted
 * @return LocalDate - the local date
 */
fun formatTimestampToLocalDate(timestamp: com.google.firebase.Timestamp): LocalDate {
    val thing = Instant.ofEpochMilli(timestamp.seconds).atZone(ZoneId.systemDefault()).toLocalDate()
    return timestamp.toDate()
        .toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}