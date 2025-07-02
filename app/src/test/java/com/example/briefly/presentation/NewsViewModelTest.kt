package com.example.briefly.presentation

import com.example.briefly.NewsViewModel
import com.example.briefly.core.Result
import com.example.briefly.domain.model.NewsItem
import com.example.briefly.domain.repository.NewsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val newsRepository: NewsRepository = mockk()
    private lateinit var viewModel: NewsViewModel

    @Before
    fun setUp() {
        viewModel = NewsViewModel(newsRepository)
    }

    @Test
    fun `emits loading then success state`() = runTest {
        val newsItem = NewsItem(
            source = "CNN",
            author = "Jacopo Prisco",
            title = "Long-dead satellite emits strong radio signal, puzzling astronomers",
            publishedDate = "30 Jun 11:05",
            imageUrl = "https://image.url",
            url = "https://cnn.com/article",
            content = "Astronomers in Australia picked up a strange radio signal...",
        )
        val articles = listOf(newsItem)
        val successResponse = Result.Success(articles)

        coEvery { newsRepository.getTopHeadlines(any()) } returns flow {
            emit(Result.Loading())
            delay(10)
            emit(successResponse)
        }

        val states = mutableListOf<NewsListState>()
        val job = launch {
            viewModel.state.toList(states)
        }

        viewModel.getTopHeadlines("us")

        advanceUntilIdle()

        assert(states[0] == NewsListState(isLoading = true))
        assert(states[1] == NewsListState(newsItems = articles))

        coVerify { newsRepository.getTopHeadlines("us") }

        job.cancel()
    }

    @Test
    fun `emits loading then error state`() = runTest {
        coEvery { newsRepository.getTopHeadlines(any()) } returns flow {
            emit(Result.Loading())
            delay(10)
            emit(Result.Error("Something went wrong"))
        }

        val states = mutableListOf<NewsListState>()
        val job = launch {
            viewModel.state.toList(states)
        }

        viewModel.getTopHeadlines("us")

        advanceUntilIdle()

        assert(states.size == 2)
        assert(states[0] == NewsListState(isLoading = true))
        assert(states[1] == NewsListState(error = "Something went wrong"))

        job.cancel()
    }
}
