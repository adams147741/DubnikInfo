package com.example.dubnikinfo.domain.repository

import com.example.dubnikinfo.data.local.news.NewsDao
import com.example.dubnikinfo.data.local.news.NewsLine
import com.example.dubnikinfo.data.local.news.toNewsLine
import com.example.dubnikinfo.data.local.news.toEntity

interface NewsRepository {
    suspend fun getCurrentActualities(): List<NewsLine>
    suspend fun getOnlineActualities(): List<NewsLine>
    suspend fun getLocalActualities(): List<NewsLine>
}

class NewsRepositoryImpl(
    private val rssDataSource: RssDataSource,
    private val newsDao: NewsDao,
) : NewsRepository {
    override suspend fun getCurrentActualities(): List<NewsLine>{
        val loadedNews = rssDataSource.fetchActualities()
        if (loadedNews.isNotEmpty()) {
            newsDao.deleteAll()
            newsDao.insertAll(loadedNews.map { it.toEntity() })
            return loadedNews
        } else {
            return newsDao.getAll().map { it.toNewsLine() }
        }
    }
    override suspend fun getOnlineActualities(): List<NewsLine> {
        val loadedNews = rssDataSource.fetchActualities()
        return loadedNews
    }
    override suspend fun getLocalActualities(): List<NewsLine> {
        val loadedNews = newsDao.getAll()
        return loadedNews.map { it.toNewsLine() }
    }
}