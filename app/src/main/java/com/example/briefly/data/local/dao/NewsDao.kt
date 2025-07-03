package com.example.briefly.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.briefly.data.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getNews(): Flow<List<NewsEntity>>

    @Upsert
    suspend fun insertNews(news: List<NewsEntity>)
}