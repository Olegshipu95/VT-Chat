package itmo.high_perf_sys.chat.tests;

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
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        createUserRequest = new CreateUserAccountRequest("John", "Doe", "johndoe@example.com", "Brief", "City", null, "url");
        updateUserRequest = new UpdateUserInfoRequest(userId, "John", "Smith", "johnsmith@example.com", "New Brief", "New City", null, "new-url");
    }

    @Test
    void createAccount_shouldReturnNewId_whenAccountCreatedSuccessfully() {
        doNothing().when(userRepository).saveNewUserAccount(
                any(UUID.class), anyString(), anyString(), anyString(), anyString(), anyString(), any(), anyString()
        );
        doNothing().when(usersChatsRepository).save(any(UsersChats.class));
        UUID result = userService.createAccount(createUserRequest);

        assertNotNull(result);
        verify(userRepository, times(1)).saveNewUserAccount(
                any(UUID.class), anyString(), anyString(), anyString(), anyString(), anyString(), any(), anyString()
        );
        verify(usersChatsRepository, times(1)).save(any(UsersChats.class));
    }

    @Test
    void updateAccount_shouldThrowException_whenAccountDoesNotExist() {
        when(userRepository.findUserAccountById(userId)).thenReturn(null);

        assertThrows(UserAccountNotFoundException.class, () -> userService.updateAccount(updateUserRequest));
    }

    @Test
    void updateAccount_shouldReturnUserId_whenUpdateIsSuccessful() {
        when(userRepository.findUserAccountById(userId)).thenReturn(new User());
        when(userRepository.updateUserAccount(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(1);

        UUID result = userService.updateAccount(updateUserRequest);

        assertEquals(userId, result);
        verify(userRepository, times(1)).updateUserAccount(
                eq(userId), anyString(), anyString(), anyString(), anyString(), anyString(), any(), anyString()
        );
    }

    @Test
    void getAccountById_shouldThrowException_whenAccountNotFound() {
        when(userRepository.findUserAccountById(userId)).thenReturn(null);

        assertThrows(UserAccountNotFoundException.class, () -> userService.getAccountById(userId));
    }

    @Test
    void getAccountById_shouldReturnAccountInfo_whenAccountExists() {
        User user = new User(userId, "John", "Doe", "johndoe@example.com", "Brief", "City", null, "url");
        when(userRepository.findUserAccountById(userId)).thenReturn(user);

        GetUserInfoResponse result = userService.getAccountById(userId);

        assertNotNull(result);
        assertEquals(user.getId(), result.userid());
        verify(userRepository, times(1)).findUserAccountById(userId);
    }

    @Test
    void deleteAccountById_shouldThrowException_whenAccountNotFound() {
        when(userRepository.findUserAccountById(userId)).thenReturn(null);

        assertThrows(UserAccountNotFoundException.class, () -> userService.deleteAccountById(userId));
    }

    @Test
    void deleteAccountById_shouldDeleteAccount_whenAccountExists() {
        when(userRepository.findUserAccountById(userId)).thenReturn(new User());

        userService.deleteAccountById(userId);

        verify(userRepository, times(1)).deleteUserAccountById(userId);
    }
}