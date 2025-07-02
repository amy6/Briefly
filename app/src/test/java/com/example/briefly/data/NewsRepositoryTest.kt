package com.example.briefly.data

import com.example.briefly.core.Result
import com.example.briefly.data.remote.NewsApiService
import com.example.briefly.data.remote.repository.NewsRepositoryImpl
import com.example.briefly.data.remote.dto.NewsArticleDto
import com.example.briefly.data.remote.dto.NewsResponseDto
import com.example.briefly.data.remote.dto.NewsSourceDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NewsRepositoryTest {

    private val newsApiService: NewsApiService = mockk()
    private lateinit var repository: NewsRepositoryImpl

    @Before
    fun setUp() {
        repository = NewsRepositoryImpl(newsApiService, "dummy-api-key")
    }

    @Test
    fun `getTopHeadlines should return a list of news items`() = runTest {
        val sourceDto = NewsSourceDto(id = "1", name = "CNN")
        val newsArticleDto = NewsArticleDto(
            source = sourceDto,
            author = "Jacopo Prisco",
            title = "Long-dead satellite emits strong radio signal, puzzling astronomers",
            description = "A NASA satellite that’s been orbiting as space junk since 1967...",
            url = "https://cnn.com/article",
            urlToImage = "https://image.url",
            publishedAt = "2025-06-30T11:05:00Z",
            content = "Astronomers in Australia picked up a strange radio signal..."
        )
        val responseDto = NewsResponseDto(
            articles = listOf(newsArticleDto),
            status = "ok",
            totalResults = 1
        )

        val country = "us"
        val apiKey = "dummy-api-key"

        coEvery { newsApiService.getTopHeadlines(country, apiKey) } returns responseDto

        val flowEmissions = repository.getTopHeadlines(country).toList()

        assertEquals(2, flowEmissions.size)
        assert(flowEmissions[0] is Result.Loading)
        assert(flowEmissions[1] is Result.Success)

        val result = flowEmissions[1].data

        assertEquals(1, result?.size)
        assertEquals("CNN", result?.first()?.source)
        assertEquals("Jacopo Prisco", result?.first()?.author)
        assertEquals(
            "Long-dead satellite emits strong radio signal, puzzling astronomers",
            result?.first()?.title
        )

        coVerify(exactly = 1) { newsApiService.getTopHeadlines(country, apiKey) }
    }


}