package com.example.briefly.presentation.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.briefly.core.Result.Error
import com.example.briefly.core.Result.Loading
import com.example.briefly.core.Result.Success
import com.example.briefly.domain.usecase.GetNewsListUseCase
import com.example.briefly.domain.usecase.RefreshNewsListUseCase
import com.example.briefly.presentation.NewsListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    val getNewsListUseCase: GetNewsListUseCase,
    val refreshNewsListUseCase: RefreshNewsListUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<NewsListState>(NewsListState())
    val state = _state.asStateFlow()

    init {
        getNewsList()
        refreshNewsList()
    }

    fun getNewsList() {
        viewModelScope.launch {
            getNewsListUseCase()
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
                .collect { result ->
                    _state.update {
                        it.copy(
                            news = result,
                            isLoading = false,
                            error = null
                        )
                    }
                }
        }
    }

    fun refreshNewsList() {
        _state.update {
            it.copy(
                isLoading = true,
            )
        }
        viewModelScope.launch {
            val result = refreshNewsListUseCase()
            when (result) {
                is Success -> _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
                is Loading -> Unit
                is Error -> _state.update {
                    it.copy(
                        isLoading = false,
                        error = result.message
                    )
                }

            }
        }
    }
}