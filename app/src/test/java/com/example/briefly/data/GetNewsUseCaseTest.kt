package com.example.briefly.data

import com.example.briefly.core.Result
import com.example.briefly.data.usecase.GetNewsUseCaseImpl
import com.example.briefly.domain.model.NewsItem
import com.example.briefly.domain.repository.NewsRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetNewsUseCaseTest {

    private val newsRepository: NewsRepository = mockk()
    private lateinit var useCase: GetNewsUseCaseImpl

    @Before
    fun setup() {
        useCase = GetNewsUseCaseImpl(newsRepository)
    }

    @Test
    fun `invoke returns flow with success result`() = runTest {
        val news = listOf(
            NewsItem(
                source = "CNN",
                category = "Jacopo Prisco",
                title = "Long-dead satellite emits strong radio signal, puzzling astronomers",
                publishedDate = "30 Jun 11:05",
                imageUrl = "https://image.url",
                url = "https://cnn.com/article",
                content = "Astronomers in Australia picked up a strange radio signal...",
            )
        )
        coEvery { newsRepository.getTopHeadlines() } returns flowOf(Result.Success(news))

        val result = useCase().first()

        assert(result is Result.Success<List<NewsItem>>)
        assertEquals(news, (result as Result.Success<List<NewsItem>>).data)
    }

    @Test
    fun `invoke returns flow with error result`() = runTest {
        val errorMessage = "Something went wrong"
        coEvery { newsRepository.getTopHeadlines() } returns flowOf(Result.Error(errorMessage))

        val result = useCase().first()

        assert(result is Result.Error)
        assertEquals(errorMessage, (result as Result.Error).message)
    }

    @Test
    fun `invoke returns flow with loading result`() = runTest {
        coEvery { newsRepository.getTopHeadlines() } returns flowOf(Result.Loading())

        val result = useCase().first()

        assert(result is Result.Loading)
    }
}
