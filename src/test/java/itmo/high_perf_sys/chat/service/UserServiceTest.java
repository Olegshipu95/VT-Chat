package itmo.high_perf_sys.chat.service;


import itmo.high_perf_sys.chat.dto.chat.request.CreateChatRequest;
import itmo.high_perf_sys.chat.dto.chat.response.*;
import itmo.high_perf_sys.chat.dto.customer.request.CreateUserAccountRequest;
import itmo.high_perf_sys.chat.dto.customer.request.UpdateUserInfoRequest;
import itmo.high_perf_sys.chat.entity.*;
import itmo.high_perf_sys.chat.exception.UserAccountNotFoundException;
import itmo.high_perf_sys.chat.repository.UserRepository;
import itmo.high_perf_sys.chat.repository.chat.ChatRepository;
import itmo.high_perf_sys.chat.repository.chat.UsersChatsRepository;
import itmo.high_perf_sys.chat.service.ChatService;
import itmo.high_perf_sys.chat.service.MessageService;
import itmo.high_perf_sys.chat.service.UsersChatsService;
import itmo.high_perf_sys.chat.utils.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.context.request.async.DeferredResult;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserRepository userRepository;
    private UsersChatsRepository usersChatsRepository;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        usersChatsRepository = mock(UsersChatsRepository.class);
        customerService = new CustomerService(userRepository, usersChatsRepository);
    }

    @Test
    void testFindById() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = customerService.findById(userId);

        assertEquals(userId, result.getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testCreateAccount() {
        CreateUserAccountRequest request = mock(CreateUserAccountRequest.class);

        UUID userId = UUID.randomUUID();
        doNothing().when(userRepository).saveNewUserAccount(any(), any(), any(), any(), any(), any(), any(), any());
        doNothing().when(usersChatsRepository).save(any(UsersChats.class));

        UUID result = customerService.createAccount(request);

        verify(userRepository, times(1)).saveNewUserAccount(any(), any(), any(), any(), any(), any(), any(), any());
        verify(usersChatsRepository, times(1)).save(any(UsersChats.class));
    }

    @Test
    void testUpdateAccount() {
        UUID userId = UUID.randomUUID();
        UpdateUserInfoRequest request = mock(UpdateUserInfoRequest.class);
        User existingUser = new User();

        when(userRepository.findUserAccountById(userId)).thenReturn(existingUser);
        when(userRepository.updateUserAccount(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(1);

        customerService.updateAccount(request);

        verify(userRepository, times(1)).findUserAccountById(any());
        verify(userRepository, times(1)).updateUserAccount(any(), any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void testDeleteAccountById() {
        UUID userId = UUID.randomUUID();
        User user = new User();

        when(userRepository.findUserAccountById(userId)).thenReturn(user);
        doNothing().when(userRepository).deleteUserAccountById(userId);

        customerService.deleteAccountById(userId);

        verify(userRepository, times(1)).findUserAccountById(userId);
        verify(userRepository, times(1)).deleteUserAccountById(userId);
    }
}