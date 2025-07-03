package com.example.briefly.presentation.news_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.briefly.presentation.EmptyNewsScreen
import com.example.briefly.presentation.NewsListState
import com.example.briefly.presentation.Screen
import com.example.briefly.presentation.news_list.components.NewsList

@Composable
fun NewsListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    newsListViewModel: NewsListViewModel = hiltViewModel()
) {

    val newsListState by newsListViewModel.state.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when (val state = newsListState) {
            is NewsListState.Error -> EmptyNewsScreen(
                message = state.message,
                onRetry = { newsListViewModel.getNewsList() }
            )

            NewsListState.Loading -> CircularProgressIndicator()

            is NewsListState.Success -> NewsList(
                news = state.newsItems,
                onItemClick = {
                    navController.navigate("${Screen.NewsDetailScreen.route}?${it.id}")
                })
        }
    }

}