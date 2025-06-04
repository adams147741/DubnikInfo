package com.example.dubnikinfo.domain.repository

import com.example.dubnikinfo.data.local.NewsDao
import com.example.dubnikinfo.data.local.NewsLine
import com.example.dubnikinfo.data.local.toNewsLine
import com.example.dubnikinfo.data.local.toEntity

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
        val loadedNewsLine = loadedNews.firstOrNull()
        val localNewsLine = newsDao.getFirst()
        if (loadedNews.isNotEmpty() && localNewsLine?.link != loadedNewsLine?.link && newsDao.getAll().isNotEmpty()) {
            newsDao.deleteAll()
            newsDao.insertAll(loadedNews.map { it.toEntity() })
            return loadedNews
        } else if (newsDao.getAll().isEmpty()) {
            newsDao.insertAll(loadedNews.map { it.toEntity() })
            return loadedNews
        }
        return newsDao.getAll().map { it.toNewsLine() }
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