package chatcore.customer;

import user.dto.request.CreateUserAccountRequest;
import user.dto.request.UpdateUserInfoRequest;
import user.dto.response.GetUserInfoResponse;
import user.entity.User;
import user.entity.UsersChats;
import user.exception.UserAccountNotFoundException;
import user.repository.UserRepository;
import user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import user.service.UsersChatsServiceClient;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerApplicationTests {
    private UserRepository userRepository;
    private UsersChatsServiceClient usersChatsService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        usersChatsService = mock(UsersChatsServiceClient.class);
        userService = new UserService(userRepository, usersChatsService);
    }
    @Test
    void testFindById() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Mono<User> resultMono = userService.findById(userId);

        StepVerifier.create(resultMono)
                .expectNextMatches(result -> {
                    assertNotNull(result);
                    assertEquals(userId, result.getId());
                    return true;
                })
                .verifyComplete();

        verify(userRepository, times(1)).findById(userId);
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

        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(usersChatsService.save(any(UsersChats.class))).thenReturn(null);

        Mono<UUID> resultMono = userService.createAccount(request);

        StepVerifier.create(resultMono)
                .expectNextMatches(result -> {
                    assertNotNull(result);
                    return true;
                })
                .verifyComplete();

        verify(userRepository, times(1)).save(any(User.class));
        verify(usersChatsService, times(1)).save(any(UsersChats.class));
    }

    @Test
    void testUpdateAccount() {
        UUID userId = UUID.randomUUID();
        UpdateUserInfoRequest request = mock(UpdateUserInfoRequest.class);
        User existingUser = new User();

        when(request.userId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        Mono<UUID> resultMono = userService.updateAccount(request);

        StepVerifier.create(resultMono)
                .expectNextMatches(result -> {
                    assertEquals(userId, result);
                    return true;
                })
                .verifyComplete();

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }
    @Test
    void testUpdateAccountThrowsException() {
        UUID userId = UUID.randomUUID();
        UpdateUserInfoRequest request = mock(UpdateUserInfoRequest.class);

        when(request.userId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(null);

        StepVerifier.create(userService.updateAccount(request))
                .expectError(UserAccountNotFoundException.class)
                .verify();

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetAccountById() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Mono<GetUserInfoResponse> resultMono = userService.getAccountById(userId);

        StepVerifier.create(resultMono)
                .expectNextMatches(result -> {
                    assertNotNull(result);
                    assertEquals(userId, result.userid());
                    return true;
                })
                .verifyComplete();

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetAccountByIdThrowsException() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(null);

        StepVerifier.create(userService.getAccountById(userId))
                .expectError(UserAccountNotFoundException.class)
                .verify();

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testDeleteAccountById() {
        UUID userId = UUID.randomUUID();
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(userId);

        Mono<Void> resultMono = userService.deleteAccountById(userId);

        StepVerifier.create(resultMono)
                .verifyComplete();

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteAccountByIdThrowsException() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(null);

        StepVerifier.create(userService.deleteAccountById(userId))
                .expectError(UserAccountNotFoundException.class)
                .verify();

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testUpdateAccountInvalidUserId() {
        UUID invalidUserId = UUID.randomUUID();
        UpdateUserInfoRequest request = mock(UpdateUserInfoRequest.class);

        when(request.userId()).thenReturn(invalidUserId);
        when(userRepository.findById(invalidUserId)).thenReturn(null);

        StepVerifier.create(userService.updateAccount(request))
                .expectError(UserAccountNotFoundException.class)
                .verify();

        verify(userRepository, times(1)).findById(invalidUserId);
        verify(userRepository, never()).save(any(User.class));
    }
}