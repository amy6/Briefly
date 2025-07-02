package com.example.briefly.domain.model

data class NewsResponse(
    val news: List<NewsItem>,
    val status: String,
    val totalResults: Int
)
