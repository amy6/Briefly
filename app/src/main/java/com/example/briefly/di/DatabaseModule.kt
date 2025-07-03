package com.example.briefly.di

import android.content.Context
import androidx.room.Room
import com.example.briefly.data.local.NewsDatabase
import com.example.briefly.data.local.dao.NewsDao
import com.example.briefly.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            Constants.DB_NAME
        ).build()
    }

    @Provides
    fun provideNewsDao(db: NewsDatabase): NewsDao = db.newsDao()
}
