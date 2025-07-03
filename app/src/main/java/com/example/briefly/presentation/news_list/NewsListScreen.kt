package com.example.briefly.presentation.news_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
            NewsListState.Empty -> EmptyNewsScreen(onRetry = { newsListViewModel.getNewsList() })

            is NewsListState.Error -> Text(
                text = state.message,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
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