package com.example.briefly.presentation

import com.example.briefly.domain.model.NewsItem

data class NewsListState(
    val news: List<NewsItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
