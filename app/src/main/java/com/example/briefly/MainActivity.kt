package com.example.briefly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.briefly.presentation.NewsApp
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
                NewsApp()
            }
        }
    }
}