package com.example.briefly.data.usecase

import com.example.briefly.domain.model.NewsItem
import com.example.briefly.domain.repository.NewsRepository
import com.example.briefly.domain.usecase.GetNewsByIdUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsByIdUseCaseImpl @Inject constructor(
    private val newsRepository: NewsRepository
) : GetNewsByIdUseCase {
    override suspend fun invoke(input: String): Flow<NewsItem> {
        return newsRepository.getNewsById(input)
    }
}