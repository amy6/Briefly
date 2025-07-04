package com.example.briefly.presentation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.briefly.presentation.news_detail.NewsDetailScreen
import com.example.briefly.presentation.news_list.NewsListScreen
import com.example.briefly.presentation.news_list.NewsListViewModel
import com.example.briefly.util.Constants.ARGUMENT_ID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsApp(
    newsListViewModel: NewsListViewModel = hiltViewModel()
) {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.NewsListScreen.route,
        modifier = Modifier
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