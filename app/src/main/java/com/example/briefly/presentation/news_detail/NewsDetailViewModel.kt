package com.example.briefly.presentation.news_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.briefly.core.Result
import com.example.briefly.domain.usecase.GetNewsByIdUseCase
import com.example.briefly.util.Constants.ARGUMENT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val getNewsByIdUseCase: GetNewsByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow<NewsDetailState>(NewsDetailState())
    val state = _state

    init {
        savedStateHandle.get<String>(ARGUMENT_ID)?.let { id ->
            getNewsById(id)
        }
    }

    fun getNewsById(id: String) {
        viewModelScope.launch {
            getNewsByIdUseCase(id).onEach { result ->
                _state.value = when (result) {
                    is Result.Loading -> NewsDetailState(isLoading = true)

                    is Result.Success -> NewsDetailState(newsContent = result.data)

                    is Result.Error -> NewsDetailState(error = result.message.orEmpty())
                }
            }.collect()
        }
    }
}