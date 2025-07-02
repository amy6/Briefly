package com.example.briefly.data.usecase

import com.example.briefly.core.Result
import com.example.briefly.domain.model.NewsItem
import com.example.briefly.domain.repository.NewsRepository
import com.example.briefly.domain.usecase.GetNewsByIdUseCase
import kotlinx.coroutines.flow.Flow

class GetNewsByIdUseCaseImpl(
    private val newsRepository: NewsRepository
) : GetNewsByIdUseCase {
    override suspend fun invoke(input: String): Flow<Result<NewsItem>> {
        return newsRepository.getNewsById(input)
    }
}