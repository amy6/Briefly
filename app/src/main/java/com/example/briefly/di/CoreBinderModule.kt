package com.example.briefly.di

import com.example.briefly.domain.network.NetworkUtils
import com.example.briefly.network.NetworkUtilsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreBinderModule {

    @Binds
    abstract fun bindNetworkUtils(
        networkUtilsImpl: NetworkUtilsImpl
    ): NetworkUtils

}