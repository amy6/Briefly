package com.example.briefly.presentation.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.briefly.core.Result
import com.example.briefly.domain.usecase.GetNewsListUseCase
import com.example.briefly.presentation.NewsListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    val getNewsListUseCase: GetNewsListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<NewsListState>(NewsListState.Loading)
    val state = _state.asStateFlow()

    init {
        getNewsList()
    }

    fun getNewsList() {
        viewModelScope.launch {
            getNewsListUseCase()
                .collect { result ->
                    _state.value =
                        when (result) {
                            is Result.Loading -> NewsListState.Loading

                            is Result.Success -> when (result.data.isNullOrEmpty()) {
                                true -> NewsListState.Error("No news articles found")
                                false -> NewsListState.Success(result.data)
                            }

                            is Result.Error -> NewsListState.Error(
                                result.message ?: "Something went wrong"
                            )

                        }
                }
        }
    }
}