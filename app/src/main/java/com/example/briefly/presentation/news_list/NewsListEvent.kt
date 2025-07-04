package com.example.briefly.presentation.news_list

sealed interface NewsListEvent {
    data class ShowToast(val message: String) : NewsListEvent
}