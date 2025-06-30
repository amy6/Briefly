package com.example.briefly.presentation.news_list.components

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.briefly.NewsViewModel
import com.example.briefly.presentation.Screen

@Composable
fun NewsListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {

    val newsViewModel: NewsViewModel = hiltViewModel()
    val newsListState = newsViewModel.state.collectAsState().value

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (newsListState.newsItems.isNotEmpty()) {
            NewsList(
                news = newsListState.newsItems,
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