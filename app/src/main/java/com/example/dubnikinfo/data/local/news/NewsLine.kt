package com.example.dubnikinfo.data.local.news

data class NewsLine(
    val title: String,
    val date: String,
    val dateStamp: Long?,
    val description: String,
    val link: String,
)
