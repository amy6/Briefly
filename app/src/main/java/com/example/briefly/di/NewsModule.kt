package com.example.briefly.di

import com.example.briefly.data.remote.repository.NewsRepositoryImpl
import com.example.briefly.data.usecase.GetNewsByIdUseCaseImpl
import com.example.briefly.data.usecase.GetNewsUseCaseImpl
import com.example.briefly.data.usecase.RefreshNewsListUseCaseImpl
import com.example.briefly.domain.repository.NewsRepository
import com.example.briefly.domain.usecase.GetNewsByIdUseCase
import com.example.briefly.domain.usecase.GetNewsListUseCase
import com.example.briefly.domain.usecase.RefreshNewsListUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NewsModule {

    @Binds
    abstract fun bindNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository

    @Binds
    abstract fun bindNewsUseCase(
        newsUseCaseImpl: GetNewsUseCaseImpl
    ): GetNewsListUseCase

    @Binds
    abstract fun bindNewsByIdUseCase(
        newsByIdUseCaseImpl: GetNewsByIdUseCaseImpl
    ): GetNewsByIdUseCase

    @Binds
    abstract fun bindRefreshNewsListUseCase(
        refreshNewsListUseCaseImpl: RefreshNewsListUseCaseImpl
    ): RefreshNewsListUseCase

}