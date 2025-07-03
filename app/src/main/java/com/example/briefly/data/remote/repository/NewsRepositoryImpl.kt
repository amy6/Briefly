package com.example.briefly.data.remote.repository

import com.example.briefly.core.Result
import com.example.briefly.data.remote.NewsApiService
import com.example.briefly.data.remote.dto.toNewsByIdResponse
import com.example.briefly.data.remote.dto.toNewsResponse
import com.example.briefly.data.remote.util.NetworkUtils
import com.example.briefly.data.remote.util.safeApiFlow
import com.example.briefly.domain.model.NewsItem
import com.example.briefly.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class NewsRepositoryImpl @Inject constructor(
    val newsApiService: NewsApiService,
    @Named("api_key") val apiKey: String,
    val networkUtils: NetworkUtils,
) : NewsRepository {
    override suspend fun getNewsList(): Flow<Result<List<NewsItem>>> = safeApiFlow(networkUtils) {
        newsApiService.getNewsList(apiKey).toNewsResponse().news
    }

    override suspend fun getNewsById(id: String): Flow<Result<NewsItem>> =
        safeApiFlow(networkUtils) {
            val response = newsApiService.getNewsById(id, apiKey).toNewsByIdResponse()
            response.content ?: throw IllegalArgumentException("No article by ID $id found")
        }
}