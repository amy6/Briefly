package com.example.briefly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.briefly.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    val newsRepository: NewsRepository
) : ViewModel() {

    fun getTopHeadlines(country: String, apiKey: String) {
        viewModelScope.launch {
            newsRepository.getTopHeadlines(country, apiKey)
                .collectLatest { result ->
                    println("Response received  : ${result.data}")
                }
        }

    }
}