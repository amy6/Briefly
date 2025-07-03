package com.example.briefly.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.briefly.data.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getNews(): Flow<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNews(news: List<NewsEntity>)

    @Query("SELECT * FROM news WHERE id = :id")
    fun getNewsById(id: String): Flow<NewsEntity>

    @Query("UPDATE news SET content = :content WHERE id = :id")
    suspend fun updateArticleContent(id: String, content: String)

}