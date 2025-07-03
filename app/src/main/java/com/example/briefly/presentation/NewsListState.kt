package com.example.briefly.presentation

import com.example.briefly.domain.model.NewsItem

sealed class NewsListState {
    object Loading : NewsListState()
    object Empty : NewsListState()
    data class Success(val newsItems: List<NewsItem>) : NewsListState()
    data class Error(val message: String) : NewsListState()
}
