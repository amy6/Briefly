package com.example.briefly.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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

@Composable
fun NewsApp(newsListViewModel: NewsListViewModel = hiltViewModel()) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Screen.NewsListScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.NewsListScreen.route)
            {
                NewsListScreen(
                    navController = navController,
                    newsListViewModel = newsListViewModel,
                )
            }
            composable(
                route = "${Screen.NewsDetailScreen.route}?{id}",
                arguments = listOf(
                    navArgument(name = "id") {
                        type = NavType.StringType
                        nullable = false
                    }
                )) {
                NewsDetailScreen()
            }

        }

    }
}