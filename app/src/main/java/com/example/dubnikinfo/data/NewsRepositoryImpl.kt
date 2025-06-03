package com.example.dubnikinfo.data

import com.example.dubnikinfo.domain.repository.NewsRepository

class NewsRepositoryImpl(
    private val rssDataSource: RssDataSource
) : NewsRepository {
    override suspend fun getActualities(): List<NewsLine> {
        return rssDataSource.fetchActualities()
    }
}