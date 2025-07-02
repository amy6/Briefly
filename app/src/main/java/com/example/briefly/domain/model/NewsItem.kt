package com.example.briefly.domain.model

data class NewsItem(
    val id: String,
    val title: String?,
    val imageUrl: String?,
    val publishedDate: String?,
    val content: String?,
    val category: String?,
    val source: String?,
    val url: String?
)

val emptyNewsItem = NewsItem(
    id = "",
    title = null,
    imageUrl = null,
    publishedDate = null,
    content = null,
    category = null,
    source = null,
    url = null
)
