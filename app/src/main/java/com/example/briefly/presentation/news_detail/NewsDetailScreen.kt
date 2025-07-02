package com.example.briefly.presentation.news_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.briefly.presentation.news_detail.components.NewsContent

@Composable
fun NewsDetailScreen(
    modifier: Modifier = Modifier,
    newsDetailViewModel: NewsDetailViewModel = hiltViewModel()
) {
    val newsState by newsDetailViewModel.state.collectAsState()
    val news = newsState.newsContent
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        news?.let {
            NewsContent(
                title = it.title.orEmpty(),
                content = it.content,
                imageUrl = it.imageUrl,
            )
        }

        if (newsState.isLoading) {
            CircularProgressIndicator()
        }

        if (newsState.error.isNotEmpty()) {
            Text(
                "Something went wrong",
                textAlign = TextAlign.Center,
            )
        }
    }
}