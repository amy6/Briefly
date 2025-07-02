package com.example.briefly.domain.model

data class NewsItem(
    val title: String?,
    val imageUrl: String?,
    val publishedDate: String?,
    val content: String?,
    val category: String?,
    val source: String?,
    val url: String?
)