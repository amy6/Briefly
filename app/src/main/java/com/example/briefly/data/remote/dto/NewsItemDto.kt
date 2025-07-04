package com.example.briefly.data.remote.dto

import com.example.briefly.data.local.entity.NewsEntity
import com.example.briefly.data.remote.util.DateFormatter.formatDate
import com.example.briefly.domain.model.NewsItem

data class NewsItemDto(
    val id: String,
    val webTitle: String?,
    val fields: NewsFieldsDto?,
    val sectionName: String?,
    val webPublicationDate: String?,
    val webUrl: String?,
)

// Move to a Mapper class in the core module if transformations are complex or reused extensively

fun NewsItemDto.toNewsItem(): NewsItem {
    return NewsItem(
        id = id,
        title = webTitle,
        imageUrl = fields?.thumbnail,
        publishedDate = webPublicationDate?.formatDate(),
        content = fields?.bodyText,
        category = sectionName,
        source = fields?.publication,
        url = webUrl
    )
}

fun NewsItemDto.toNewsEntity(): NewsEntity {
    return NewsEntity(
        id = id,
        title = webTitle,
        imageUrl = fields?.thumbnail,
        publishedAt = webPublicationDate,
        source = fields?.publication,
        category = sectionName,
        content = fields?.bodyText,
        url = webUrl
    )
}