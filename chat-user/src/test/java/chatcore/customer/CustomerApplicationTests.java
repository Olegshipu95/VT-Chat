package chatcore.customer;

import user.dto.request.CreateUserAccountRequest;
import user.dto.request.UpdateUserInfoRequest;
import user.dto.response.GetUserInfoResponse;
import user.entity.User;
import user.entity.UsersChats;
import user.exception.UserAccountNotFoundException;
import user.repository.UserRepository;
import user.service.UserService;
import user.service.UsersChatsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerApplicationTests {
    private UserRepository userRepository;
    private UsersChatsService usersChatsService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        usersChatsService = mock(UsersChatsService.class);
        userService = new UserService(userRepository, usersChatsService);
    }
    @Test
    void testFindById() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Mono.just(user));

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

        when(userRepository.save(any(User.class))).thenReturn(Mono.just(new User()));
        when(usersChatsService.save(any(UsersChats.class))).thenReturn(Mono.empty());

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
        when(userRepository.findById(userId)).thenReturn(Mono.just(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(existingUser));

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
        when(userRepository.findById(userId)).thenReturn(Mono.empty());

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

        when(userRepository.findById(userId)).thenReturn(Mono.just(user));

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

        when(userRepository.findById(userId)).thenReturn(Mono.empty());

        StepVerifier.create(userService.getAccountById(userId))
                .expectError(UserAccountNotFoundException.class)
                .verify();

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testDeleteAccountById() {
        UUID userId = UUID.randomUUID();
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Mono.just(user));
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

        when(userRepository.findById(userId)).thenReturn(Mono.empty());

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
        when(userRepository.findById(invalidUserId)).thenReturn(Mono.empty());

        StepVerifier.create(userService.updateAccount(request))
                .expectError(UserAccountNotFoundException.class)
                .verify();

        verify(userRepository, times(1)).findById(invalidUserId);
        verify(userRepository, never()).save(any(User.class));
    }
}