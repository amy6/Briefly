package com.example.briefly.presentation.news_list.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.briefly.domain.model.NewsItem

@Composable
fun NewsList(
    modifier: Modifier = Modifier,
    news: List<NewsItem> = emptyList(),
    onItemClick: (NewsItem) -> Unit = {}
) {
    if (news.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
        ) {
            items(news) {
                it.apply {
                    NewsListItem(
                        title = title.orEmpty(),
                        publishedAt = publishedDate,
                        imageUrl = imageUrl,
                        source = source,
                        author = category,
                        onClick = { onItemClick(it) }
                    )
                }
            }

        }
    }

}
