package com.example.briefly.presentation

import com.example.briefly.domain.model.NewsItem

data class NewsListState(
    val isLoading: Boolean = false,
    val newsItems: List<NewsItem> = emptyList(),
    val error: String = ""
)
