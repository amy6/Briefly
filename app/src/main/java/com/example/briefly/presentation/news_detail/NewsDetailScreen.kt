package com.example.briefly.presentation.news_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.briefly.presentation.news_detail.components.NewsContent

@Composable
fun NewsDetailScreen(
    modifier: Modifier = Modifier,
    newsDetailViewModel: NewsDetailViewModel = hiltViewModel()
) {
    val newsState by newsDetailViewModel.state.collectAsStateWithLifecycle()
    val news = newsState.newsContent

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when {
            newsState.isLoading -> {
                CircularProgressIndicator()
            }

            newsState.error.isNotEmpty() -> {
                Text(
                    newsState.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                )
            }

            else ->
                news?.let {
                    NewsContent(
                        title = it.title.orEmpty(),
                        content = it.content,
                        imageUrl = it.imageUrl,
                        category = it.category,
                        publishedDate = it.publishedDate,
                    )
                }
        }
    }
}