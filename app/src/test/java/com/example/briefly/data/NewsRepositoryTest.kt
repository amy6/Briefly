package com.example.briefly.data

import com.example.briefly.core.Result
import com.example.briefly.data.remote.NewsApiService
import com.example.briefly.data.remote.dto.NewsFieldsDto
import com.example.briefly.data.remote.dto.NewsItemDto
import com.example.briefly.data.remote.dto.NewsResponseDto
import com.example.briefly.data.remote.dto.NewsResultsDto
import com.example.briefly.data.remote.repository.NewsRepositoryImpl
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
        val fieldsDto = NewsFieldsDto(
            bodyText = "Astronomers in Australia picked up a strange radio signal...",
            publication = "CNN",
            thumbnail = "https://image.url"
        )
        val newsItemDto = NewsItemDto(
            id = "1",
            fields = fieldsDto,
            sectionName = "Jacopo Prisco",
            webTitle = "Long-dead satellite emits strong radio signal, puzzling astronomers",
            webUrl = "https://cnn.com/article",
            webPublicationDate = "2025-06-30T11:05:00Z",
        )
        val resultsDto = NewsResultsDto(
            results = listOf(newsItemDto),
            status = "ok",
            total = 1
        )

        val responseDto = NewsResponseDto(
            response = resultsDto
        )

        val apiKey = "dummy-api-key"

        coEvery { newsApiService.getNews(apiKey) } returns responseDto

        val flowEmissions = repository.getTopHeadlines().toList()

        assertEquals(2, flowEmissions.size)
        assert(flowEmissions[0] is Result.Loading)
        assert(flowEmissions[1] is Result.Success)

        val result = flowEmissions[1].data

        assertEquals(1, result?.size)
        assertEquals("CNN", result?.first()?.source)
        assertEquals("Jacopo Prisco", result?.first()?.category)
        assertEquals(
            "Long-dead satellite emits strong radio signal, puzzling astronomers",
            result?.first()?.title
        )

        coVerify(exactly = 1) { newsApiService.getNews(apiKey) }
    }


}