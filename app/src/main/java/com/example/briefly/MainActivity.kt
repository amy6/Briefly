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
import androidx.lifecycle.lifecycleScope
import com.example.briefly.presentation.news_list.components.NewsList
import com.example.briefly.ui.theme.BrieflyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

        lifecycleScope.launch(Dispatchers.Main + coroutineExceptionHandler) {
            newsViewModel.getTopHeadlines("us", "")
        }

        setContent {
            BrieflyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NewsList(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}