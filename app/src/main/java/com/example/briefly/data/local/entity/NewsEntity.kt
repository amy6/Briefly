package com.example.briefly.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

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
