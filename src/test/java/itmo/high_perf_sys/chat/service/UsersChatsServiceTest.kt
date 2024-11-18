package itmo.high_perf_sys.chat.service

import itmo.high_perf_sys.chat.entity.UsersChats
import itmo.high_perf_sys.chat.repository.chat.UsersChatsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.junit.Assert.*
import java.util.*


class UsersChatsServiceTest {

    @Mock
    private lateinit var usersChatsRepository: UsersChatsRepository

    @InjectMocks
    private lateinit var usersChatsService: UsersChatsService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testFindByUserId() {
        val userId = UUID.randomUUID()
        val usersChats = UsersChats().apply {
            id = UUID.randomUUID()
            userId = userId
        }

        `when`(usersChatsRepository.findByUserId(userId)).thenReturn(Optional.of(usersChats))

        val result = usersChatsService.findByUserId(userId)

        assertNotNull(result)
        assertTrue(result.isPresent)
        assertEquals(usersChats, result.get())
    }

    @Test
    fun testFindByUserIdNotFound() {
        val userId = UUID.randomUUID()

        `when`(usersChatsRepository.findByUserId(userId)).thenReturn(Optional.empty())

        val result = usersChatsService.findByUserId(userId)

        assertNotNull(result)
        assertTrue(result.isEmpty)
    }

    @Test
    fun testSave() {
        val usersChats = UsersChats().apply {
            id = UUID.randomUUID()
            userId = UUID.randomUUID()
        }

        usersChatsService.save(usersChats)

        verify(usersChatsRepository, times(1)).save(usersChats)
    }

    @Test
    fun testFindIdsByChatId() {
        val chatId = UUID.randomUUID()
        val userIds = listOf(UUID.randomUUID(), UUID.randomUUID())

        `when`(usersChatsRepository.findIdsByChatId(chatId)).thenReturn(userIds)

        val result = usersChatsService.findIdsByChatId(chatId)

        assertNotNull(result)
        assertEquals(2, result.size)
        assertEquals(userIds, result)
    }

    @Test
    fun testFindById() {
        val id = UUID.randomUUID()
        val usersChats = UsersChats().apply {
            id = id
            userId = UUID.randomUUID()
        }

        `when`(usersChatsRepository.findById(id)).thenReturn(Optional.of(usersChats))

        val result = usersChatsService.findById(id)

        assertNotNull(result)
        assertTrue(result.isPresent)
        assertEquals(usersChats, result.get())
    }

    @Test
    fun testFindByIdNotFound() {
        val id = UUID.randomUUID()

        `when`(usersChatsRepository.findById(id)).thenReturn(Optional.empty())

        val result = usersChatsService.findById(id)

        assertNotNull(result)
        assertTrue(result.isEmpty)
    }

    @Test
    fun testCountByChatId() {
        val chatId = UUID.randomUUID()
        val count = 5

        `when`(usersChatsRepository.countByChatId(chatId)).thenReturn(count)

        val result = usersChatsService.countByChatId(chatId)

        assertEquals(count, result)
    }
}
