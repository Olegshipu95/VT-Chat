package itmo.high_perf_sys.chat.service

import itmo.high_perf_sys.chat.entity.Message
import itmo.high_perf_sys.chat.repository.chat.MessageRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.junit.Assert.*
import java.util.*


class MessageServiceTest {

    @Mock
    private lateinit var messageRepository: MessageRepository

    @InjectMocks
    private lateinit var messageService: MessageService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testSaveMessage() {
        val message = Message().apply {
            id = UUID.randomUUID()
            text = "Test Message"
        }

        messageService.save(message)

        verify(messageRepository, times(1)).save(message)
    }

    @Test
    fun testFindLastByChatId() {
        val chatId = UUID.randomUUID()
        val message = Message().apply {
            id = UUID.randomUUID()
            text = "Last Message"
        }

        `when`(messageRepository.findLastByChatId(chatId)).thenReturn(Optional.of(message))

        val result = messageService.findLastByChatId(chatId)

        assertNotNull(result)
        assertEquals(message, result.get())
    }

    @Test
    fun testFindLastByChatIdNotFound() {
        val chatId = UUID.randomUUID()

        `when`(messageRepository.findLastByChatId(chatId)).thenReturn(Optional.empty())

        val result = messageService.findLastByChatId(chatId)

        assertNotNull(result)
        assertEquals(false, result.isPresent)
    }

    @Test
    fun testFindByTextContainingAndChatId() {
        val chatId = UUID.randomUUID()
        val request = "Test"
        val pageable: Pageable = PageRequest.of(0, 10)
        val messages = listOf(
            Message().apply {
                id = UUID.randomUUID()
                text = "Test Message 1"
            },
            Message().apply {
                id = UUID.randomUUID()
                text = "Test Message 2"
            }
        )

        `when`(messageRepository.findByTextContainingAndChatId(chatId, request, pageable))
            .thenReturn(PageImpl(messages))

        val result = messageService.findByTextContainingAndChatId(chatId, request, pageable)

        assertNotNull(result)
        assertEquals(2, result.content.size)
    }

    @Test
    fun testFindByChatId() {
        val chatId = UUID.randomUUID()
        val pageable: Pageable = PageRequest.of(0, 10)
        val messages = listOf(
            Message().apply {
                id = UUID.randomUUID()
                text = "Message 1"
            },
            Message().apply {
                id = UUID.randomUUID()
                text = "Message 2"
            }
        )

        `when`(messageRepository.findByChatId(chatId, pageable)).thenReturn(PageImpl(messages))

        val result = messageService.findByChatId(chatId, pageable)

        assertNotNull(result)
        assertEquals(2, result.content.size)
    }
}
