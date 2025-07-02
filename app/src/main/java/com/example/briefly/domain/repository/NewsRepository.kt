package com.example.briefly.domain.repository

import com.example.briefly.core.Result
import com.example.briefly.domain.model.NewsItem
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsList(): Flow<Result<List<NewsItem>>>
    suspend fun getNewsById(id: String): Flow<Result<NewsItem>>
}