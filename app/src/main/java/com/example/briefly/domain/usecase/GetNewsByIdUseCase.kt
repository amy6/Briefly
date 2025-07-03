package com.example.briefly.domain.usecase

import com.example.briefly.core.SuspendingUseCase
import com.example.briefly.domain.model.NewsItem
import kotlinx.coroutines.flow.Flow

interface GetNewsByIdUseCase : SuspendingUseCase<String, Flow<NewsItem>>