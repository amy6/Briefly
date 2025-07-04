package com.example.briefly.presentation.news_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.briefly.domain.usecase.GetNewsByIdUseCase
import com.example.briefly.util.Constants.ARGUMENT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    val getNewsByIdUseCase: GetNewsByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(NewsDetailState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<String>(ARGUMENT_ID)?.let { id ->
            if (id.isNotBlank()) {
                getNewsById(id)
            }
        }
    }

    fun getNewsById(id: String) {
        _state.update {
            it.copy(
                isLoading = true,
            )
        }
        viewModelScope.launch {
            var receivedContent = false
            getNewsByIdUseCase(id).onEach { newsItem ->
                receivedContent = newsItem.content != null
                _state.update {
                    it.copy(
                        newsContent = newsItem,
                        isLoading = !receivedContent,
                    )
                }
            }.launchIn(this)

            delay(3_000)
            if (!receivedContent) {
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }
}