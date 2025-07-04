package com.example.briefly.presentation.news_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.briefly.presentation.EmptyNewsScreen
import com.example.briefly.presentation.Screen
import com.example.briefly.presentation.news_list.components.NewsList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    newsListViewModel: NewsListViewModel = hiltViewModel()
) {

    val newsListState by newsListViewModel.state.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when {
            newsListState.error.isNullOrEmpty().not() -> EmptyNewsScreen(
                message = newsListState.error,
                onRetry = { newsListViewModel.getNewsList() }
            )

            newsListState.isLoading -> CircularProgressIndicator()

            else -> NewsList(
                news = newsListState.news,
                isRefreshing = newsListState.isRefreshing,
                onItemClick = {
                    navController.navigate("${Screen.NewsDetailScreen.route}?${it.id}")
                },
                onRefresh = {
                    newsListViewModel.refreshNewsList()
                }
            )
        }
    }

}