package com.example.briefly.domain.model

data class NewsResponse(
    val articles: List<NewsItem>,
    val status: String,
    val totalResults: Int
)
