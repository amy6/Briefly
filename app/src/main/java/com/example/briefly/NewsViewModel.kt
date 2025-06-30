package com.example.briefly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.briefly.core.Result.Success
import com.example.briefly.domain.repository.NewsRepository
import com.example.briefly.presentation.NewsListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    val newsRepository: NewsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<NewsListState>(NewsListState.Loading)
    val state = _state

    fun getTopHeadlines(country: String, apiKey: String) {
        viewModelScope.launch {
            _state.update { NewsListState.Loading }
            newsRepository.getTopHeadlines(country, apiKey)
                .collectLatest { result ->
                    println("Response received  : ${result.data}")
                    _state.update {
                        when (result) {
                            is Success -> NewsListState.Success(result.data ?: emptyList())
                            else -> NewsListState.Error(result.message ?: "Something went wrong")
                        }
                    }
                }
        }

    }
}