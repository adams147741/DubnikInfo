package com.example.dubnikinfo.domain.repository

import com.example.dubnikinfo.data.local.news.NewsLine
import com.example.dubnikinfo.utils.parseDateToTimestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import org.jsoup.Jsoup

class RssDataSource {
    suspend fun fetchActualities(): List<NewsLine> = withContext(Dispatchers.IO) {
        return@withContext try {
            val doc = Jsoup.connect("https://www.obec-dubnik.sk/api/rss/").get()
            val items = doc.select("item")
            items.mapNotNull {
                val category = it.selectFirst("category")?.text()?.trim()
                if (category != "Aktuality") return@mapNotNull null

                val title = it.selectFirst("title")?.text().orEmpty()
                val date = it.selectFirst("pubDate")?.text().orEmpty()
                val description = it.selectFirst("description")?.text().orEmpty()
                val link = it.selectFirst("link")?.text().orEmpty()

                NewsLine(title = title,date = date,description = description,link = link,dateStamp = parseDateToTimestamp(date))
            }
        } catch (e : IOException) {
            emptyList()
        }
    }
}