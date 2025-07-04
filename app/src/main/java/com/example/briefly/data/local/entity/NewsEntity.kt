package com.example.briefly.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.briefly.data.remote.util.DateFormatter.formatDate
import com.example.briefly.domain.model.NewsItem

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey val id: String,
    val title: String?,
    val imageUrl: String?,
    val publishedAt: String?,
    val source: String?,
    val category: String?,
    val content: String? = null,
    val url: String? = null,
)

fun NewsEntity.toNewsItem(): NewsItem {
    return NewsItem(
        id = id,
        title = title,
        imageUrl = imageUrl,
        publishedDate = publishedAt?.formatDate(),
        source = source,
        category = category,
        content = content,
        url = url
    )
}
