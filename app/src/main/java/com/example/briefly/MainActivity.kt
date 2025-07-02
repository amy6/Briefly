package com.example.briefly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.briefly.presentation.Screen
import com.example.briefly.presentation.news_detail.NewsDetailScreen
import com.example.briefly.presentation.news_list.NewsListScreen
import com.example.briefly.presentation.news_list.NewsListViewModel
import com.example.briefly.ui.theme.BrieflyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: NewsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.getNewsList()

        setContent {
            BrieflyTheme {
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
                                newsListViewModel = viewModel
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
        }
    }
}