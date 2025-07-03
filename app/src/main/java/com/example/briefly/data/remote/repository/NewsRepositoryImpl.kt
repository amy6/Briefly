package com.example.briefly.data.remote.repository

import com.example.briefly.core.Result
import com.example.briefly.data.remote.NewsApiService
import com.example.briefly.data.remote.dto.toNewsByIdResponse
import com.example.briefly.data.remote.dto.toNewsResponse
import com.example.briefly.data.remote.util.NetworkUtils
import com.example.briefly.domain.model.NewsItem
import com.example.briefly.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Named

class NewsRepositoryImpl @Inject constructor(
    val newsApiService: NewsApiService,
    @Named("api_key") val apiKey: String,
    val networkUtils: NetworkUtils,
) : NewsRepository {
    override suspend fun getNewsList(): Flow<Result<List<NewsItem>>> = flow {
        emit(Result.Loading())

        if (!networkUtils.isConnected()) {
            emit(Result.Error("No internet connection"))
            return@flow
        }

        try {
            val response = newsApiService.getNewsList(apiKey).toNewsResponse()
            emit(Result.Success(response.news))
        } catch (e: HttpException) {
            emit(Result.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Result.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    override suspend fun getNewsById(id: String): Flow<Result<NewsItem>> = flow {
        emit(Result.Loading())

        if (!networkUtils.isConnected()) {
            emit(Result.Error("No internet connection"))
            return@flow
        }

        try {
            val response = newsApiService.getNewsById(id, apiKey).toNewsByIdResponse()
            response.content?.let {
                emit(Result.Success(it))
            } ?: emit(Result.Error("No article by ID $id found"))
        } catch (e: HttpException) {
            emit(Result.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Result.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Result.Error("Something went wrong: ${e.localizedMessage}"))
        }
    }
}