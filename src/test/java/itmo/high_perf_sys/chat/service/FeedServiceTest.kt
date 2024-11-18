package itmo.high_perf_sys.chat.service

import itmo.high_perf_sys.chat.dto.feed.request.CreatePostRequest
import itmo.high_perf_sys.chat.dto.feed.request.DeletePostRequest
import itmo.high_perf_sys.chat.dto.feed.response.FeedResponse
import itmo.high_perf_sys.chat.entity.Post
import itmo.high_perf_sys.chat.entity.User
import itmo.high_perf_sys.chat.repository.FeedRepository
import itmo.high_perf_sys.chat.repository.UserRepository
import itmo.high_perf_sys.chat.utils.ErrorMessages
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.sql.Timestamp
import org.junit.Assert.*
import java.util.*

class FeedServiceTest {

    @Mock
    private lateinit var feedRepository: FeedRepository

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var feedService: FeedService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testCreateFeed() {
        val createPostRequest = CreatePostRequest(
            userId = UUID.randomUUID(),
            title = "Test Title",
            text = "Test Text",
            images = listOf("image1.jpg", "image2.jpg")
        )

        val user = User().apply {
            id = createPostRequest.userId
        }

        `when`(userRepository.findById(createPostRequest.userId)).thenReturn(Optional.of(user))
        `when`(feedRepository.save(any())).thenAnswer { invocation ->
            val post = invocation.getArgument<Post>(0)
            post.id = UUID.randomUUID()
            post
        }

        val postId = feedService.createFeed(createPostRequest)

        assertNotNull(postId)
        verify(feedRepository, times(1)).save(any())
    }

    @Test
    fun testCreateFeedUserNotFound() {
        val createPostRequest = CreatePostRequest(
            userId = UUID.randomUUID(),
            title = "Test Title",
            text = "Test Text",
            images = listOf("image1.jpg", "image2.jpg")
        )

        `when`(userRepository.findById(createPostRequest.userId)).thenReturn(Optional.empty())

        assertFailsWith<RuntimeException> { feedService.createFeed(createPostRequest) }
    }

    @Test
    fun testDeletePost() {
        val deletePostRequest = DeletePostRequest(
            feedId = UUID.randomUUID(),
            userId = UUID.randomUUID()
        )

        val post = Post().apply {
            id = deletePostRequest.feedId
            user = User().apply { id = deletePostRequest.userId }
        }

        `when`(feedRepository.findById(deletePostRequest.feedId)).thenReturn(Optional.of(post))

        feedService.deletePost(deletePostRequest)

        verify(feedRepository, times(1)).deleteById(deletePostRequest.feedId)
    }

    @Test
    fun testDeletePostNotFound() {
        val deletePostRequest = DeletePostRequest(
            feedId = UUID.randomUUID(),
            userId = UUID.randomUUID()
        )

        `when`(feedRepository.findById(deletePostRequest.feedId)).thenReturn(Optional.empty())

        assertFailsWith<RuntimeException> { feedService.deletePost(deletePostRequest) }
    }

    @Test
    fun testDeletePostNoPermission() {
        val deletePostRequest = DeletePostRequest(
            feedId = UUID.randomUUID(),
            userId = UUID.randomUUID()
        )

        val post = Post().apply {
            id = deletePostRequest.feedId
            user = User().apply { id = UUID.randomUUID() }
        }

        `when`(feedRepository.findById(deletePostRequest.feedId)).thenReturn(Optional.of(post))

        assertFailsWith<RuntimeException> { feedService.deletePost(deletePostRequest) }
    }

    @Test
    fun testGetFeedByUserId() {
        val userId = UUID.randomUUID()
        val page = 0L
        val count = 10L

        val posts = listOf(
            Post().apply {
                id = UUID.randomUUID()
                user = User().apply { id = userId }
                title = "Post 1"
                text = "Text 1"
                images = listOf("image1.jpg")
                postedTime = Timestamp(System.currentTimeMillis())
            },
            Post().apply {
                id = UUID.randomUUID()
                user = User().apply { id = userId }
                title = "Post 2"
                text = "Text 2"
                images = listOf("image2.jpg")
                postedTime = Timestamp(System.currentTimeMillis())
            }
        )

        `when`(feedRepository.findByUserId(userId, PageRequest.of(page.toInt(), count.toInt())))
            .thenReturn(PageImpl(posts))

        val response = feedService.getFeedByUserId(userId, page, count)

        assertNotNull(response)
        assertEquals(2, response.response.size)
    }

    @Test
    fun testGetFeedByUserIdError() {
        val userId = UUID.randomUUID()
        val page = 0L
        val count = 10L

        `when`(feedRepository.findByUserId(userId, PageRequest.of(page.toInt(), count.toInt())))
            .thenThrow(RuntimeException(ErrorMessages.ERROR_DB_REQUEST))

        assertFailsWith<RuntimeException> { feedService.getFeedByUserId(userId, page, count) }
    }
}
