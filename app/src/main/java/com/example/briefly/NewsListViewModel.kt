package com.example.briefly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.briefly.core.Result
import com.example.briefly.core.Result.Success
import com.example.briefly.domain.usecase.GetNewsListUseCase
import com.example.briefly.presentation.NewsListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    val getNewsListUseCase: GetNewsListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<NewsListState>(NewsListState())
    val state = _state

    fun getNewsList() {
        viewModelScope.launch {
            getNewsListUseCase()
                .onEach { result ->
                    _state.value = when (result) {
                        is Result.Loading -> NewsListState(isLoading = true)

                        is Success -> NewsListState(newsItems = result.data.orEmpty())

                        is Result.Error -> NewsListState(error = result.message.orEmpty())
                    }
                }.collect()
        }

    }
}