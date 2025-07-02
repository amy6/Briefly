package com.example.briefly.presentation.news_list

import android.net.Uri
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
import androidx.navigation.NavController
import com.example.briefly.presentation.Screen
import com.example.briefly.presentation.news_list.components.NewsList

@Composable
fun NewsListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    newsListViewModel: NewsListViewModel = hiltViewModel()
) {

    val newsListState by newsListViewModel.state.collectAsState()
    val news = newsListState.newsItems
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (news.isNotEmpty()) {
            NewsList(
                news = news,
                onItemClick = {
                    val encodedTitle = Uri.encode(it.title)
                    val encodedContent = Uri.encode(it.content)
                    val encodedImageUrl = Uri.encode(it.imageUrl)

                    navController.navigate("${Screen.NewsDetailScreen.route}?title=$encodedTitle&content=$encodedContent&imageUrl=$encodedImageUrl")
                })
        }

        if (newsListState.isLoading) {
            CircularProgressIndicator()
        }

        if (newsListState.error.isNotEmpty()) {
            Text(
                "Something went wrong",
                textAlign = TextAlign.Center,
            )
        }
    }

}