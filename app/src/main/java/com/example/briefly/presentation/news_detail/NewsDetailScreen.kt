package com.example.briefly.presentation.news_detail

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.briefly.R
import com.example.briefly.presentation.news_detail.components.NewsContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    modifier: Modifier = Modifier,
    newsDetailViewModel: NewsDetailViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val newsState by newsDetailViewModel.state.collectAsStateWithLifecycle()
    val news = newsState.newsContent
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.action_navigate_back),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    val url = newsState.newsContent?.url.orEmpty()
                    AnimatedVisibility(url.isNotBlank() == true) {
                        IconButton(onClick = {
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "Check this out")
                                putExtra(Intent.EXTRA_TEXT, url)
                            }
                            context.startActivity(Intent.createChooser(intent, "Share via"))
                        }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = stringResource(R.string.action_share),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            news?.let {
                NewsContent(
                    title = it.title.orEmpty(),
                    content = it.content,
                    imageUrl = it.imageUrl,
                    category = it.category,
                    publishedDate = it.publishedDate,
                    isContentLoading = newsState.isLoading
                )
            }
        }
    }
}