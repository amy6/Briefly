package com.example.briefly.di

import com.example.briefly.BuildConfig
import com.example.briefly.data.remote.NewsApiService
import com.example.briefly.data.remote.repository.NewsRepositoryImpl
import com.example.briefly.data.usecase.GetNewsUseCaseImpl
import com.example.briefly.domain.repository.NewsRepository
import com.example.briefly.domain.usecase.GetNewsListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return client
    }

    @Provides
    @Singleton
    fun provideNewsApiService(client: OkHttpClient): NewsApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsApiService: NewsApiService): NewsRepository {
        return NewsRepositoryImpl(
            newsApiService = newsApiService,
            apiKey = BuildConfig.API_KEY
        )
    }

    @Provides
    @Singleton
    fun provideNewsUseCase(newsRepository: NewsRepository): GetNewsListUseCase {
        return GetNewsUseCaseImpl(newsRepository)
    }

}