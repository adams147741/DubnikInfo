package com.example.dubnikinfo.domain.repository

import com.example.dubnikinfo.data.local.news.NewsDao
import com.example.dubnikinfo.data.local.news.NewsLine
import com.example.dubnikinfo.data.local.news.toNewsLine
import com.example.dubnikinfo.data.local.news.toEntity
import com.example.dubnikinfo.data.remote.RssDataSource

interface NewsRepository {
    suspend fun getCurrentActualities(): List<NewsLine>
    suspend fun getOnlineActualities(): List<NewsLine>
    suspend fun getLocalActualities(): List<NewsLine>
    suspend fun update()
}

class NewsRepositoryImpl(
    private val rssDataSource: RssDataSource,
    private val newsDao: NewsDao,
) : NewsRepository {
    /**
     * Returns a list of news lines
     * @return List<NewsLine>
     */
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

    /**
     * Returns a list of news lines from the online source
     * @return List<NewsLine>
     */
    override suspend fun getOnlineActualities(): List<NewsLine> {
        val loadedNews = rssDataSource.fetchActualities()
        return loadedNews
    }

    /**
     * Returns a list of news lines from the local source
     * @return List<NewsLine>
     */
    override suspend fun getLocalActualities(): List<NewsLine> {
        val loadedNews = newsDao.getAll()
        return loadedNews.map { it.toNewsLine() }
    }

    /**
     * Updates the local database with the news from the online source
     */
    override suspend fun update() {
        val loadedNews = rssDataSource.fetchActualities()
        newsDao.deleteAll()
        newsDao.insertAll(loadedNews.map { it.toEntity() })
    }
}