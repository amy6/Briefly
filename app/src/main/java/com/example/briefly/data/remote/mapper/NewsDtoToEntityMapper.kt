package com.example.briefly.data.remote.mapper

import com.example.briefly.data.local.entity.NewsEntity
import com.example.briefly.data.remote.dto.NewsItemDto

object NewsDtoToEntityMapper {
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
}