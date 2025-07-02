package com.example.briefly.data.remote.repository

import com.example.briefly.core.Result
import com.example.briefly.data.remote.NewsApiService
import com.example.briefly.data.remote.dto.toNewsResponse
import com.example.briefly.domain.model.NewsItem
import com.example.briefly.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    val newsApiService: NewsApiService,
    val apiKey: String,
) : NewsRepository {
    override suspend fun getTopHeadlines(): Flow<Result<List<NewsItem>>> = flow {
        try {
            emit(Result.Loading())
            val response = newsApiService.getNews(apiKey).toNewsResponse()
            emit(Result.Success(response.news))
        } catch (e: HttpException) {
            emit(Result.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Result.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}