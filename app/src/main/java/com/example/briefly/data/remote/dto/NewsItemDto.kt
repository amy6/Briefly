package com.example.briefly.data.remote.dto

data class NewsItemDto(
    val id: String,
    val webTitle: String?,
    val fields: NewsFieldsDto?,
    val sectionName: String?,
    val webPublicationDate: String?,
    val webUrl: String?,
)