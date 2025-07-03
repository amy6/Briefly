package com.example.briefly.domain.usecase

import com.example.briefly.core.NoInputSuspendingUseCase
import com.example.briefly.domain.model.NewsItem
import kotlinx.coroutines.flow.Flow

interface GetNewsListUseCase : NoInputSuspendingUseCase<Flow<List<NewsItem>>>