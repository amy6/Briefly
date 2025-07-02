package com.example.briefly.data.remote

import com.example.briefly.data.remote.dto.NewsResponseDto
import com.example.briefly.util.Constants.DEFAULT_SHOW_FIELDS
import com.example.briefly.util.Constants.PAGE_SIZE
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("search")
    suspend fun getNewsList(
        @Query("api-key") apiKey: String,
        @Query("show-fields") showFields: String = DEFAULT_SHOW_FIELDS,
        @Query("page-size") pageSize: Int = PAGE_SIZE,
    ): NewsResponseDto

}