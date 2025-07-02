package com.example.briefly.data.usecase

import com.example.briefly.core.Result
import com.example.briefly.domain.model.NewsItem
import com.example.briefly.domain.repository.NewsRepository
import com.example.briefly.domain.usecase.GetNewsListUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetNewsUseCaseImpl @Inject constructor(
    private val newsRepository: NewsRepository
) : GetNewsListUseCase {
    override suspend fun invoke(input: String): Flow<Result<List<NewsItem>>> {
        return newsRepository.getTopHeadlines(input)
    }

}