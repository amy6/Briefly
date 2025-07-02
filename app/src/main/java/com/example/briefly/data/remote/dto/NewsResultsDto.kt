package com.example.briefly.data.remote.dto

import com.example.briefly.domain.model.NewsByIdResponse
import com.example.briefly.domain.model.NewsResponse

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

fun NewsResponseDto.toNewsResponse() = with(response) {
    NewsResponse(
        news = results?.map { it.toNewsItem() } ?: emptyList(),
        status = status ?: "",
        totalResults = total ?: 0
    )
}

fun NewsByIdResponseDto.toNewsByIdResponse() = with(response) {
    NewsByIdResponse(
        content = content!!.toNewsItem(),
        status = status ?: "",
        totalResults = total ?: 0

    )
}