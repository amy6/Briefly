package com.example.briefly.data.remote.dto

data class NewsResponseDto(
    val response: NewsResultsDto
)

data class NewsResultsDto(
    val results: List<NewsItemDto>?,
    val status: String?,
    val total: Int?
)

data class NewsByIdResponseDto(
    val response: NewsByIdContentDto
)

data class NewsByIdContentDto(
    val content: NewsItemDto?,
    val status: String?,
    val total: Int?
)