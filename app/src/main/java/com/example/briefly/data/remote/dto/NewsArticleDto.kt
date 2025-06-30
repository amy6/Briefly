package com.example.briefly.data.remote.dto

import com.example.briefly.data.remote.util.DateFormatter.formatDate
import com.example.briefly.domain.model.NewsItem

data class NewsArticleDto(
    val source: NewsSourceDto?,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)

fun NewsArticleDto.toNewsItem(): NewsItem {
    return NewsItem(
        title = title,
        imageUrl = urlToImage,
        publishedDate = publishedAt?.formatDate(),
        content = content,
        author = author,
        source = source?.name,
        url = url
    )

}