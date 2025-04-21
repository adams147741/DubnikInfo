package com.example.dubnikinfo.data

import androidx.compose.ui.Modifier

data class NewsLine(
    val headline: String,
    val date: String,
    val text: String,
    val modifier: Modifier = Modifier
)
