package com.example.briefly.presentation

sealed class Screen(val route: String) {
    object NewsListScreen : Screen("news_list")
    object NewsDetailScreen : Screen("news_detail")
}