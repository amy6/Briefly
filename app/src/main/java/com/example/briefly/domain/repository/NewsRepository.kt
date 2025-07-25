package com.example.briefly.domain.repository

import com.example.briefly.core.Result
import com.example.briefly.domain.model.NewsItem
import kotlinx.coroutines.flow.Flow

// Repository is defined in domain layer for easy swapping in tests

interface NewsRepository {
    suspend fun refreshNewsList(): Result<Unit>
    suspend fun getNewsList(): Flow<List<NewsItem>>
    suspend fun getNewsById(id: String): Flow<NewsItem>
}