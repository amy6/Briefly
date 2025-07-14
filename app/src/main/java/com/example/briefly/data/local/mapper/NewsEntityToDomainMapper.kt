package com.example.briefly.data.local.mapper

import com.example.briefly.data.local.entity.NewsEntity
import com.example.briefly.data.remote.util.DateFormatter.formatDate
import com.example.briefly.domain.model.NewsItem

object NewsEntityToDomainMapper {
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
}