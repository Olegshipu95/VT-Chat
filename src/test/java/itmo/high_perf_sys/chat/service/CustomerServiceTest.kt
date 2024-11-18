package itmo.high_perf_sys.chat.service

import itmo.high_perf_sys.chat.dto.customer.request.CreateUserAccountRequest
import itmo.high_perf_sys.chat.dto.customer.request.UpdateUserInfoRequest
import itmo.high_perf_sys.chat.dto.customer.response.GetUserInfoResponse
import itmo.high_perf_sys.chat.entity.User
import itmo.high_perf_sys.chat.entity.UsersChats
import itmo.high_perf_sys.chat.exception.UserAccountNotFoundException
import itmo.high_perf_sys.chat.exception.UserAccountWasNotInsertException
import itmo.high_perf_sys.chat.repository.UserRepository
import itmo.high_perf_sys.chat.repository.chat.UsersChatsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*
import org.junit.Assert.*



class CustomerServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var usersChatsRepository: UsersChatsRepository

    @InjectMocks
    private lateinit var customerService: CustomerService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testCreateAccount() {
        val request = CreateUserAccountRequest(
            name = "John",
            surname = "Doe",
            email = "john.doe@example.com",
            briefDescription = "Developer",
            city = "New York",
            birthday = "1990-01-01",
            logoUrl = "http://example.com/logo.png"
        )

        val newId = UUID.randomUUID()
        `when`(userRepository.saveNewUserAccount(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(1)
        `when`(usersChatsRepository.save(any())).thenReturn(UsersChats())

        val userId = customerService.createAccount(request)

        assertNotNull(userId)
        assertEquals(newId, userId)
        verify(userRepository, times(1)).saveNewUserAccount(any(), any(), any(), any(), any(), any(), any(), any())
        verify(usersChatsRepository, times(1)).save(any())
    }

    @Test
    fun testUpdateAccount() {
        val request = UpdateUserInfoRequest(
            userId = UUID.randomUUID(),
            name = "Jane",
            surname = "Doe",
            email = "jane.doe@example.com",
            briefDescription = "Designer",
            city = "Los Angeles",
            birthday = "1992-02-02",
            logoUrl = "http://example.com/newlogo.png"
        )

        val existingAccount = User().apply {
            id = request.userId
        }
        `when`(userRepository.findUserAccountById(request.userId)).thenReturn(existingAccount)
        `when`(userRepository.updateUserAccount(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(1)

        val userId = customerService.updateAccount(request)

        assertNotNull(userId)
        assertEquals(request.userId, userId)
        verify(userRepository, times(1)).updateUserAccount(any(), any(), any(), any(), any(), any(), any(), any())
    }

    @Test
    fun testUpdateAccountNotFound() {
        val request = UpdateUserInfoRequest(
            userId = UUID.randomUUID(),
            name = "Jane",
            surname = "Doe",
            email = "jane.doe@example.com",
            briefDescription = "Designer",
            city = "Los Angeles",
            birthday = "1992-02-02",
            logoUrl = "http://example.com/newlogo.png"
        )

        `when`(userRepository.findUserAccountById(request.userId)).thenReturn(null)

        assertFailsWith<UserAccountNotFoundException> { customerService.updateAccount(request) }
    }

    @Test
    fun testUpdateAccountNotInserted() {
        val request = UpdateUserInfoRequest(
            userId = UUID.randomUUID(),
            name = "Jane",
            surname = "Doe",
            email = "jane.doe@example.com",
            briefDescription = "Designer",
            city = "Los Angeles",
            birthday = "1992-02-02",
            logoUrl = "http://example.com/newlogo.png"
        )

        val existingAccount = User().apply {
            id = request.userId
        }
        `when`(userRepository.findUserAccountById(request.userId)).thenReturn(existingAccount)
        `when`(userRepository.updateUserAccount(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(0)

        assertFailsWith<UserAccountWasNotInsertException> { customerService.updateAccount(request) }
    }

    @Test
    fun testGetAccountById() {
        val userId = UUID.randomUUID()
        val account = User().apply {
            id = userId
            name = "John"
            surname = "Doe"
            email = "john.doe@example.com"
            briefDescription = "Developer"
            city = "New York"
            birthday = "1990-01-01"
            logoUrl = "http://example.com/logo.png"
        }

        `when`(userRepository.findUserAccountById(userId)).thenReturn(account)

        val response = customerService.getAccountById(userId)

        assertNotNull(response)
        assertEquals(userId, response.id)
        assertEquals("John", response.name)
        assertEquals("Doe", response.surname)
        assertEquals("john.doe@example.com", response.email)
        assertEquals("Developer", response.briefDescription)
        assertEquals("New York", response.city)
        assertEquals("1990-01-01", response.birthday)
        assertEquals("http://example.com/logo.png", response.logoUrl)
    }

    @Test
    fun testGetAccountByIdNotFound() {
        val userId = UUID.randomUUID()
        `when`(userRepository.findUserAccountById(userId)).thenReturn(null)

        assertFailsWith<UserAccountNotFoundException> { customerService.getAccountById(userId) }
    }

    @Test
    fun testDeleteAccountById() {
        val userId = UUID.randomUUID()
        val account = User().apply {
            id = userId
        }

        `when`(userRepository.findUserAccountById(userId)).thenReturn(account)

        customerService.deleteAccountById(userId)

        verify(userRepository, times(1)).deleteUserAccountById(userId)
    }

    @Test
    fun testDeleteAccountByIdNotFound() {
        val userId = UUID.randomUUID()
        `when`(userRepository.findUserAccountById(userId)).thenReturn(null)

        assertFailsWith<UserAccountNotFoundException> { customerService.deleteAccountById(userId) }
    }
}
