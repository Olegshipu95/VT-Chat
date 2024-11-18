package itmo.high_perf_sys.chat.service

import itmo.high_perf_sys.chat.dto.subs.request.CreateSubRequest
import itmo.high_perf_sys.chat.dto.subs.response.SubscriptionResponse
import itmo.high_perf_sys.chat.entity.Subscribers
import itmo.high_perf_sys.chat.exception.UserAccountNotFoundException
import itmo.high_perf_sys.chat.repository.SubRepository
import itmo.high_perf_sys.chat.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime
import org.junit.Assert.*
import java.util.*


class SubscriptionServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var subscribersRepository: SubRepository

    @InjectMocks
    private lateinit var subscriptionService: SubscriptionService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testCreateSub() {
        val createSubRequest = CreateSubRequest(
            userId = UUID.randomUUID(),
            subscribedUserId = UUID.randomUUID()
        )

        `when`(userRepository.existsById(createSubRequest.userId)).thenReturn(true)
        `when`(userRepository.existsById(createSubRequest.subscribedUserId)).thenReturn(true)
        `when`(subscribersRepository.save(any())).thenAnswer { invocation ->
            val subscriber = invocation.getArgument<Subscribers>(0)
            subscriber.id = UUID.randomUUID()
            subscriber
        }

        val subId = subscriptionService.createSub(createSubRequest)

        assertNotNull(subId)
        verify(subscribersRepository, times(1)).save(any())
    }

    @Test
    fun testCreateSubUserNotFound() {
        val createSubRequest = CreateSubRequest(
            userId = UUID.randomUUID(),
            subscribedUserId = UUID.randomUUID()
        )

        `when`(userRepository.existsById(createSubRequest.userId)).thenReturn(false)

        assertFailsWith<UserAccountNotFoundException> { subscriptionService.createSub(createSubRequest) }
    }

    @Test
    fun testCreateSubSubscribedUserNotFound() {
        val createSubRequest = CreateSubRequest(
            userId = UUID.randomUUID(),
            subscribedUserId = UUID.randomUUID()
        )

        `when`(userRepository.existsById(createSubRequest.userId)).thenReturn(true)
        `when`(userRepository.existsById(createSubRequest.subscribedUserId)).thenReturn(false)

        assertFailsWith<UserAccountNotFoundException> { subscriptionService.createSub(createSubRequest) }
    }

    @Test
    fun testGetSub() {
        val subId = UUID.randomUUID()
        val subscriber = Subscribers().apply {
            id = subId
            userId = UUID.randomUUID()
            subscribedUserId = UUID.randomUUID()
            subscriptionTime = LocalDateTime.now()
        }

        `when`(subscribersRepository.findById(subId)).thenReturn(Optional.of(subscriber))

        val result = subscriptionService.getSub(subId)

        assertNotNull(result)
        assertEquals(subId, result.id)
    }

    @Test
    fun testGetSubNotFound() {
        val subId = UUID.randomUUID()

        `when`(subscribersRepository.findById(subId)).thenReturn(Optional.empty())

        assertFailsWith<IllegalArgumentException> { subscriptionService.getSub(subId) }
    }

    @Test
    fun testDeleteSub() {
        val subId = UUID.randomUUID()

        `when`(subscribersRepository.existsById(subId)).thenReturn(true)

        subscriptionService.deleteSub(subId)

        verify(subscribersRepository, times(1)).deleteById(subId)
    }

    @Test
    fun testDeleteSubNotFound() {
        val subId = UUID.randomUUID()

        `when`(subscribersRepository.existsById(subId)).thenReturn(false)

        assertFailsWith<IllegalArgumentException> { subscriptionService.deleteSub(subId) }
    }

    @Test
    fun testGetSubscriptionsByUserId() {
        val userId = UUID.randomUUID()
        val subscriptions = listOf(
            SubscriptionResponse(
                id = UUID.randomUUID(),
                userId = userId,
                subscribedUserId = UUID.randomUUID(),
                subscriptionTime = LocalDateTime.now()
            ),
            SubscriptionResponse(
                id = UUID.randomUUID(),
                userId = userId,
                subscribedUserId = UUID.randomUUID(),
                subscriptionTime = LocalDateTime.now()
            )
        )

        `when`(subscribersRepository.getSubResponseByUserId(userId)).thenReturn(subscriptions)

        val result = subscriptionService.getSubscriptionsByUserId(userId)

        assertNotNull(result)
        assertEquals(2, result.size)
    }
}
