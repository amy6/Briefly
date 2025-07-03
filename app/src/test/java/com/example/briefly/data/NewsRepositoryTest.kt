package com.example.briefly.data

import com.example.briefly.core.Result
import com.example.briefly.data.local.dao.NewsDao
import com.example.briefly.data.local.entity.NewsEntity
import com.example.briefly.data.local.entity.toNewsItem
import com.example.briefly.data.remote.NewsApiService
import com.example.briefly.data.remote.dto.NewsFieldsDto
import com.example.briefly.data.remote.dto.NewsItemDto
import com.example.briefly.data.remote.dto.NewsResponseDto
import com.example.briefly.data.remote.dto.NewsResultsDto
import com.example.briefly.data.remote.repository.NewsRepositoryImpl
import com.example.briefly.data.remote.util.NetworkUtils
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NewsRepositoryTest {

    private val newsApiService: NewsApiService = mockk()
    private val networkUtils: NetworkUtils = mockk()
    private val newsDao: NewsDao = mockk()
    private lateinit var repository: NewsRepositoryImpl

    @Before
    fun setUp() {
        repository = NewsRepositoryImpl(newsApiService, newsDao, "dummy-api-key", networkUtils)
    }

    @Test
    fun `getNewsList should return data from dao`() = runTest {
        val newsEntities = listOf(
            NewsEntity(
                id = "1",
                title = "Long-dead satellite emits strong radio signal, puzzling astronomers",
                imageUrl = "https://image.url",
                publishedAt = "30 Jun 11:05",
                content = "Astronomers in Australia picked up a strange radio signal...",
                category = "Jacopo Prisco",
                source = "CNN",
            )
        )
        val expectedNewsItems = newsEntities.map { it.toNewsItem() }

        coEvery { newsDao.getNews() } returns flowOf(newsEntities)

        val result = repository.getNewsList().first()

        assertEquals(expectedNewsItems, result)
    }

    @Test
    fun `refreshNewsList should fetch data from api and insert into db`() = runTest {
        coEvery { networkUtils.isConnected() } returns true

        val apiResponse = NewsResponseDto(
            response = NewsResultsDto(
                results = listOf(
                    NewsItemDto(
                        id = "1",
                        webTitle = "Long-dead satellite emits strong radio signal, puzzling astronomers",
                        fields = NewsFieldsDto(
                            thumbnail = "https://image.url",
                            bodyText = "Astronomers in Australia picked up a strange radio signal...",
                            publication = "CNN"
                        ),
                        webPublicationDate = "30 Jun 11:05",
                        sectionName = "Football",
                        webUrl = "https://example.url"
                    )
                ),
                status = "ok",
                total = 1
            )
        )

        coEvery { newsApiService.getNewsList(any()) } returns apiResponse

        coJustRun { newsDao.insertNews(any()) }

        val result = repository.refreshNewsList()

        assertTrue(result is Result.Success)
        coVerify { newsDao.insertNews(match { it.size == 1 && it[0].id == "1" }) }
    }

}