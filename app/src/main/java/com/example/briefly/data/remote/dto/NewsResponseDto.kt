package com.example.briefly.data.remote.dto

import com.example.briefly.domain.model.NewsResponse

data class NewsResponseDto(
    val articles: List<NewsArticleDto>?,
    val status: String?,
    val totalResults: Int?
)

fun NewsResponseDto.toNewsResponse() = NewsResponse(
    articles = articles?.map { it.toNewsItem() } ?: emptyList(),
    status = status ?: "",
    totalResults = totalResults ?: 0
)