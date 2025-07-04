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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    val getNewsListUseCase: GetNewsListUseCase,
    val refreshNewsListUseCase: RefreshNewsListUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(NewsListState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<NewsListEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getNewsList()
        refreshNewsList(fromUser = false)
    }

    fun getNewsList() {
        viewModelScope.launch {
            getNewsListUseCase()
                .collect { result ->
                    val hasData = result.isNotEmpty()
                    _state.update {
                        it.copy(
                            news = result,
                            isLoading = !hasData,
                            error = if (!hasData) "No data available" else null
                        )
                    }
                }
        }
    }

    fun refreshNewsList(fromUser: Boolean = true) {
        if (fromUser) {
            _state.update {
                it.copy(
                    isRefreshing = true
                )
            }
        }
        viewModelScope.launch {
            val result = refreshNewsListUseCase()
            when (result) {
                is Success -> _state.update {
                    it.copy(
                        isRefreshing = false,
                        error = null
                    )
                }

                is Error -> {
                    val shouldShowRetry = _state.value.news.isEmpty()
                    _state.update {
                        it.copy(
                            isRefreshing = false,
                            error = if (shouldShowRetry) result.message else null
                        )
                    }

                    if (!shouldShowRetry) {
                        showToast(result.message ?: "Something went wrong. Please try again.")
                    }
                }

                is Loading -> Unit

            }
        }
    }

    fun showToast(message: String) {
        viewModelScope.launch {
            _eventFlow.emit(NewsListEvent.ShowToast(message))
        }
    }
}
