package com.example.briefly.data.remote

import com.example.briefly.data.remote.dto.NewsResponseDto
import com.example.briefly.domain.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): NewsResponseDto // update to domain response entity


}