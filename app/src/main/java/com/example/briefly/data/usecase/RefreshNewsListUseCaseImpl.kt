package com.example.briefly.data.usecase

import com.example.briefly.core.Result
import com.example.briefly.domain.repository.NewsRepository
import com.example.briefly.domain.usecase.RefreshNewsListUseCase
import javax.inject.Inject

class RefreshNewsListUseCaseImpl @Inject constructor(
    val newsRepository: NewsRepository
) : RefreshNewsListUseCase {
    override suspend fun invoke(): Result<Unit> {
        return newsRepository.refreshNewsList()
    }
}