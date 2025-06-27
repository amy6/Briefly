package com.example.briefly.data.remote.dto

data class NewsResponseDto(
    val articles: List<NewsArticleDto>?,
    val status: String?,
    val totalResults: Int?
)