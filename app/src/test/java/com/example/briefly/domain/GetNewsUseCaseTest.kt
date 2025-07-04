package com.example.briefly.domain

import com.example.briefly.data.usecase.GetNewsUseCaseImpl
import com.example.briefly.domain.model.NewsItem
import com.example.briefly.domain.repository.NewsRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
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
    fun `usecase should return list of news items from repository`() = runTest {
        val news = listOf(
            NewsItem(
                id = "1",
                title = "Long-dead satellite emits strong radio signal, puzzling astronomers",
                imageUrl = "https://image.url",
                publishedDate = "30 Jun 11:05",
                content = "Astronomers in Australia picked up a strange radio signal...",
                category = "Jacopo Prisco",
                source = "CNN",
                url = "https://cnn.com/article",
            )
        )
        coEvery { newsRepository.getNewsList() } returns flowOf(news)

        val result = useCase().first()

        TestCase.assertEquals(result, news)
    }
}