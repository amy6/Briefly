package com.example.briefly.data.remote.repository

import com.example.briefly.core.Result
import com.example.briefly.data.local.dao.NewsDao
import com.example.briefly.data.local.entity.toNewsItem
import com.example.briefly.data.remote.NewsApiService
import com.example.briefly.data.remote.dto.toNewsEntity
import com.example.briefly.data.remote.util.NetworkUtils
import com.example.briefly.data.remote.util.safeApiFlow
import com.example.briefly.domain.model.NewsItem
import com.example.briefly.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

class NewsRepositoryImpl @Inject constructor(
    val newsApiService: NewsApiService,
    val newsDao: NewsDao,
    @Named("api_key") val apiKey: String,
    val networkUtils: NetworkUtils,
) : NewsRepository {

    override suspend fun refreshNewsList(): Result<Unit> =
        safeApiFlow(networkUtils) {
            newsApiService.getNewsList(apiKey).response.results?.map { it.toNewsEntity() }
                .orEmpty()
                .also {
                    newsDao.insertNews(it)
                }
        }

    override suspend fun getNewsList(): Flow<List<NewsItem>> =
        newsDao.getNews().map { it.map { it.toNewsItem() } }


    override suspend fun getNewsById(id: String): Flow<NewsItem> =
        newsDao.getNewsById(id).onEach { article ->
            if (article.content == null) {
                val result = safeApiFlow(networkUtils) {
                    newsApiService.getNewsById(id, apiKey)
                }
                when (result) {
                    is Result.Success -> {
                        val newsArticleFromRemote = newsApiService.getNewsById(id, apiKey)
                        val content =
                            newsArticleFromRemote.response.content?.fields?.bodyText.orEmpty()
                        newsDao.updateArticleContent(id, content)
                    }

                    is Result.Error -> println("Error fetching news article content : ${result.message}")
                    else -> Unit
                }
            }
        }.map { it.toNewsItem() }
}