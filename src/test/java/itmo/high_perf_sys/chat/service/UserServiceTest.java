package itmo.high_perf_sys.chat.service;


import itmo.high_perf_sys.chat.dto.customer.request.CreateUserAccountRequest;
import itmo.high_perf_sys.chat.dto.customer.request.UpdateUserInfoRequest;
import itmo.high_perf_sys.chat.dto.customer.response.GetUserInfoResponse;
import itmo.high_perf_sys.chat.entity.*;
import itmo.high_perf_sys.chat.exception.UserAccountNotFoundException;
import itmo.high_perf_sys.chat.repository.UserRepository;
import itmo.high_perf_sys.chat.repository.chat.UsersChatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserRepository userRepository;
    private UsersChatsRepository usersChatsRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        usersChatsRepository = mock(UsersChatsRepository.class);
        userService = new UserService(userRepository, usersChatsRepository);
    }

    @Test
    void testFindById() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.findById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testExistsById() {
        UUID userId = UUID.randomUUID();

        when(userRepository.existsById(userId)).thenReturn(true);

        boolean exists = userService.existsById(userId);

        assertTrue(exists);
        verify(userRepository, times(1)).existsById(userId);
    }

    @Test
    void testCreateAccount() {
        CreateUserAccountRequest request = mock(CreateUserAccountRequest.class);

        when(request.name()).thenReturn("John");
        when(request.surname()).thenReturn("Doe");
        when(request.email()).thenReturn("john.doe@example.com");
        when(request.city()).thenReturn("City");
        when(request.briefDescription()).thenReturn("A user");
        when(request.logoUrl()).thenReturn("http://example.com/logo.png");

        UUID result = userService.createAccount(request);

        assertNotNull(result);
        verify(userRepository, times(1)).save(any(User.class));
        verify(usersChatsRepository, times(1)).save(any(UsersChats.class));
    }

    @Test
    void testUpdateAccount() {
        UUID userId = UUID.randomUUID();
        UpdateUserInfoRequest request = mock(UpdateUserInfoRequest.class);
        User existingUser = new User();

        when(request.userId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        UUID result = userService.updateAccount(request);

        assertEquals(userId, result);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateAccountThrowsException() {
        UUID userId = UUID.randomUUID();
        UpdateUserInfoRequest request = mock(UpdateUserInfoRequest.class);

        when(request.userId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserAccountNotFoundException.class, () -> userService.updateAccount(request));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetAccountById() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        GetUserInfoResponse result = userService.getAccountById(userId);

        assertNotNull(result);
        assertEquals(userId, result.userid());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetAccountByIdThrowsException() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserAccountNotFoundException.class, () -> userService.getAccountById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testDeleteAccountById() {
        UUID userId = UUID.randomUUID();
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(userId);

        userService.deleteAccountById(userId);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteAccountByIdThrowsException() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserAccountNotFoundException.class, () -> userService.deleteAccountById(userId));
        verify(userRepository, times(1)).findById(userId);
    }
}