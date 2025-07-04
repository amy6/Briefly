package com.example.briefly.presentation

import com.example.briefly.core.Result
import com.example.briefly.domain.model.NewsItem
import com.example.briefly.domain.usecase.GetNewsListUseCase
import com.example.briefly.domain.usecase.RefreshNewsListUseCase
import com.example.briefly.presentation.news_list.NewsListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val getNewsListUseCase = mockk<GetNewsListUseCase>()
    private val refreshNewsListUseCase = mockk<RefreshNewsListUseCase>()
    private lateinit var viewModel: NewsListViewModel

    @Test
    fun `getNewsList should update state with news list and set loading false`() = runTest {
        val newsItem = NewsItem(
            id = "1",
            title = "Long-dead satellite emits strong radio signal, puzzling astronomers",
            imageUrl = "https://image.url",
            publishedDate = "30 Jun 11:05",
            content = "Astronomers in Australia picked up a strange radio signal...",
            category = "Jacopo Prisco",
            source = "CNN",
            url = "https://cnn.com/article",
        )
        val articles = listOf(newsItem)

        coEvery { getNewsListUseCase() } returns flowOf(articles)
        coEvery { refreshNewsListUseCase() } returns Result.Success(Unit)

        viewModel = NewsListViewModel(getNewsListUseCase, refreshNewsListUseCase)

        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(articles, state.news)
        assertFalse(state.isLoading)
        assertNull(state.error)
    }

    @Test
    fun `refreshNewsList should update state with error message`() = runTest {
        val errorMessage = "Something went wrong"
        coEvery { getNewsListUseCase() } returns flowOf(emptyList())
        coEvery { refreshNewsListUseCase() } returns Result.Error(errorMessage)

        viewModel = NewsListViewModel(getNewsListUseCase, refreshNewsListUseCase)

        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(errorMessage, state.error)

    }

    // Additional test cases:
    // Pull-to-refresh makes a call to refresh news list from API
    // Refresh while offline should fallback to cached data
    // If there's an API error and no cached data, shows "No data available"
    // On initial load, if there's no internet connectivity, shows "No internet connection"

    // UI test cases
    // Pull-to-refresh spinner is shown while refreshing
    // Clicking on list item navigates to detail screen
    // Cached data is immediately shown when navigated to detail screen
    // Error message is shown when there's no internet connectivity


}
