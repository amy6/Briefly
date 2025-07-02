package com.example.briefly.presentation.news_detail

import com.example.briefly.domain.model.NewsItem
import com.example.briefly.domain.model.emptyNewsItem

data class NewsDetailState(
    val isLoading: Boolean = false,
    val newsContent: NewsItem? = emptyNewsItem,
    val error: String = ""
)
