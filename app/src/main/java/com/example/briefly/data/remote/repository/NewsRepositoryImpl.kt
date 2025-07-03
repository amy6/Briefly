package com.example.briefly.data.remote.repository

import com.example.briefly.core.Result
import com.example.briefly.data.local.dao.NewsDao
import com.example.briefly.data.local.entity.toNewsItem
import com.example.briefly.data.remote.NewsApiService
import com.example.briefly.data.remote.dto.toNewsEntity
import com.example.briefly.data.remote.util.NetworkUtils
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

    override suspend fun refreshNewsList(): Result<Unit> {
        return when (networkUtils.isConnected()) {
            true -> {
                newsApiService.getNewsList(apiKey).response.results?.map { it.toNewsEntity() }
                    .orEmpty()
                    .also {
                        newsDao.insertNews(it)
                    }
                Result.Success(Unit)
            }

            false -> Result.Error("Unable to fetch data. Please check your internet connection and try again.")
        }


    }

    override suspend fun getNewsList(): Flow<List<NewsItem>> =
        newsDao.getNews().map { it.map { it.toNewsItem() } }


    override suspend fun getNewsById(id: String): Flow<NewsItem> =
        newsDao.getNewsById(id).onEach { article ->
            if (article.content == null) {
                val newsArticleFromRemote = newsApiService.getNewsById(id, apiKey)
                newsDao.updateArticleContent(
                    id,
                    newsArticleFromRemote.response.content?.fields?.bodyText.orEmpty()
                )
            }
        }.map { it.toNewsItem() }
}