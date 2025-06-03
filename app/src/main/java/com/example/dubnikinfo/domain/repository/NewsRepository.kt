package com.example.dubnikinfo.domain.repository

import com.example.dubnikinfo.data.NewsLine

interface NewsRepository {
    suspend fun getActualities(): List<NewsLine>
}