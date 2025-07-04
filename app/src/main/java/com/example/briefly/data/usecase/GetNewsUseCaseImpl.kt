package com.example.briefly.data.usecase

import com.example.briefly.domain.model.NewsItem
import com.example.briefly.domain.repository.NewsRepository
import com.example.briefly.domain.usecase.GetNewsListUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

// UseCase layer can be omitted here as it is only delegating to repository
// This can help reduce unnecessary abstraction

class GetNewsUseCaseImpl @Inject constructor(
    private val newsRepository: NewsRepository
) : GetNewsListUseCase {
    override suspend fun invoke(): Flow<List<NewsItem>> {
        return newsRepository.getNewsList()
    }

}