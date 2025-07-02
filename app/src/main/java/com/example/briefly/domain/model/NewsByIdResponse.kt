package com.example.briefly.domain.model

data class NewsByIdResponse(
    val content: NewsItem?,
    val status: String,
    val totalResults: Int
)
