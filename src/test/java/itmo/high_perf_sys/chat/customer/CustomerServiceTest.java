package itmo.high_perf_sys.chat.customer;

import itmo.high_perf_sys.chat.dto.customer.request.CreateUserAccountRequest;
import itmo.high_perf_sys.chat.dto.customer.request.UpdateUserInfoRequest;
import itmo.high_perf_sys.chat.dto.customer.response.GetUserInfoResponse;
import itmo.high_perf_sys.chat.entity.User;
import itmo.high_perf_sys.chat.entity.UsersChats;
import itmo.high_perf_sys.chat.exception.UserAccountNotFoundException;
import itmo.high_perf_sys.chat.repository.UserRepository;
import itmo.high_perf_sys.chat.repository.chat.UsersChatsRepository;
import itmo.high_perf_sys.chat.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UsersChatsRepository usersChatsRepository;

    @InjectMocks
    private UserService userService;

    private CreateUserAccountRequest createUserRequest;
    private UpdateUserInfoRequest updateUserRequest;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        createUserRequest = new CreateUserAccountRequest("John", "Doe", "johndoe@example.com", "Brief", "City", null, "url");
        updateUserRequest = new UpdateUserInfoRequest(userId, "John", "Smith", "johnsmith@example.com", "New Brief", "New City", null, "new-url");
    }

    @Test
    void createAccount_shouldReturnNewId_whenAccountCreatedSuccessfully() {

    }

    @Test
    void updateAccount_shouldThrowException_whenAccountDoesNotExist() {

    }

    @Test
    void updateAccount_shouldReturnUserId_whenUpdateIsSuccessful() {

    }

    @Test
    void getAccountById_shouldThrowException_whenAccountNotFound() {

    }

    @Test
    void getAccountById_shouldReturnAccountInfo_whenAccountExists() {

    }

    @Test
    void deleteAccountById_shouldThrowException_whenAccountNotFound() {

    }

    @Test
    void deleteAccountById_shouldDeleteAccount_whenAccountExists() {

    }
}