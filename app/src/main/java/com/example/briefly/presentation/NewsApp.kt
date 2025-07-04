package com.example.briefly.presentation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.briefly.presentation.news_detail.NewsDetailScreen
import com.example.briefly.presentation.news_list.NewsListScreen
import com.example.briefly.presentation.news_list.NewsListViewModel
import com.example.briefly.util.Constants.ARGUMENT_ID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsApp(newsListViewModel: NewsListViewModel = hiltViewModel()) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route
    val isOnNewsListScreen = currentRoute == Screen.NewsListScreen.route
    val canNavigateBack = navController.previousBackStackEntry != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { if (isOnNewsListScreen) Text(text = "Briefly") else null },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface
                ),
                navigationIcon = {
                    AnimatedVisibility(
                        visible = canNavigateBack
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.NewsListScreen.route,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            composable(route = Screen.NewsListScreen.route)
            {
                NewsListScreen(
                    navController = navController,
                    newsListViewModel = newsListViewModel,
                )
            }
            composable(
                route = "${Screen.NewsDetailScreen.route}?{$ARGUMENT_ID}",
                arguments = listOf(
                    navArgument(name = ARGUMENT_ID) {
                        type = NavType.StringType
                        nullable = false
                    }
                ),
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = LinearEasing
                        )
                    ) + slideIntoContainer(
                        animationSpec = tween(300, easing = EaseIn),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        )
                    ) + slideOutOfContainer(
                        animationSpec = tween(300, easing = EaseOut),
                        towards = AnimatedContentTransitionScope.SlideDirection.End
                    )
                }
            ) {
                NewsDetailScreen()
            }
        }

    }
}