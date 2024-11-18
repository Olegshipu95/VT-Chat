package itmo.high_perf_sys.chat.service

import itmo.high_perf_sys.chat.dto.feed.response.FeedResponse
import itmo.high_perf_sys.chat.entity.Post
import itmo.high_perf_sys.chat.repository.NewsRepository
import itmo.high_perf_sys.chat.utils.ErrorMessages
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.junit.Assert.*
import java.util.*


class NewsServiceTest {

    @Mock
    private lateinit var newsRepository: NewsRepository

    @InjectMocks
    private lateinit var newsService: NewsService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetPostsBySubscriber() {
        val userId = 1L
        val page = 0L
        val count = 10L

        val posts = listOf(
            Post().apply {
                id = UUID.randomUUID()
                title = "Post 1"
                text = "Text 1"
                images = listOf("image1.jpg")
            },
            Post().apply {
                id = UUID.randomUUID()
                title = "Post 2"
                text = "Text 2"
                images = listOf("image2.jpg")
            }
        )

        `when`(newsRepository.findPostsBySubscriber(userId, PageRequest.of(page.toInt(), count.toInt())))
            .thenReturn(PageImpl(posts))

        val response = newsService.getPostsBySubscriber(userId, page, count)

        assertNotNull(response)
        assertEquals(2, response.response.size)
    }

    @Test
    fun testGetPostsBySubscriberError() {
        val userId = 1L
        val page = 0L
        val count = 10L

        `when`(newsRepository.findPostsBySubscriber(userId, PageRequest.of(page.toInt(), count.toInt())))
            .thenThrow(RuntimeException(ErrorMessages.ERROR_DB_REQUEST))

        assertFailsWith<RuntimeException> { newsService.getPostsBySubscriber(userId, page, count) }
    }
}
