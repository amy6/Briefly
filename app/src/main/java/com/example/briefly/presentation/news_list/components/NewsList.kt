package com.example.briefly.presentation.news_list.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.briefly.NewsViewModel
import com.example.briefly.presentation.NewsListState

@Composable
fun NewsList(
    viewModel: NewsViewModel,
    modifier: Modifier = Modifier
) {

    val newsListState = viewModel.state.collectAsState().value

    when (newsListState) {
        is NewsListState.Loading -> {
            CircularProgressIndicator()
        }

        is NewsListState.Success -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
            ) {
                items(newsListState.newsItems) {
                    it.apply {
                        NewsListItem(
                            title = title ?: "",
                            publishedAt = publishedDate ?: "",
                            imageUrl = imageUrl ?: "",
                            source = source ?: "",
                            author = author ?: ""
                        )
                    }
                }

            }
        }

        is NewsListState.Error -> {
            Text("Something went wrong")
        }
    }
}
