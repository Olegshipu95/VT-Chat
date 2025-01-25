package chatcore.customer;

import user.dto.request.CreateUserAccountRequest;
import user.dto.request.UpdateUserInfoRequest;
import user.dto.response.GetUserInfoResponse;
import user.entity.User;
import user.entity.UsersChats;
import user.exception.UserAccountNotFoundException;
import user.exception.UserAccountWasNotInsertException;
import user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import user.service.UserService;
import user.service.UsersChatsServiceClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UsersChatsServiceClient usersChatsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAccount_Success() {
        CreateUserAccountRequest request = new CreateUserAccountRequest("John", "Doe", "john.doe@example.com", "Brief description", "City", LocalDate.of(1990, 1, 1), "logoUrl");
        User newUser = new User(null, "John", "Doe", "john.doe@example.com", "Brief description", "City", LocalDate.of(1990, 1, 1), "logoUrl");

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(UUID.randomUUID()); // Устанавливаем случайный UUID
            return Mono.just(user);
        });
        when(usersChatsService.save(any(UsersChats.class))).thenReturn(new UsersChats());

        Mono<UUID> result = userService.createAccount(request);

        UUID createdId = result.block();
        assertNotNull(createdId); // Проверяем, что ID не null
        verify(userRepository, times(1)).save(any(User.class));
        verify(usersChatsService, times(1)).save(any(UsersChats.class));
    }

    @Test
    public void testUpdateAccount_Success() {
        UUID userId = UUID.randomUUID();
        UpdateUserInfoRequest request = new UpdateUserInfoRequest(userId, "John", "Doe", "john.doe@example.com", "Brief description", "City", LocalDate.of(1990, 1, 1), "logoUrl");
        User existingUser = new User(userId, "OldName", "OldSurname", "old.email@example.com", "Old description", "OldCity", LocalDate.of(1990, 1, 1), "oldLogoUrl");

        when(userRepository.findById(userId)).thenReturn(Mono.just(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(existingUser));

        Mono<UUID> result = userService.updateAccount(request);

        assertEquals(userId, result.block());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateAccount_UserNotFound() {
        UUID userId = UUID.randomUUID();
        UpdateUserInfoRequest request = new UpdateUserInfoRequest(userId, "John", "Doe", "john.doe@example.com", "Brief description", "City", LocalDate.of(1990, 1, 1), "logoUrl");

        when(userRepository.findById(userId)).thenReturn(Mono.empty());

        assertThrows(NullPointerException.class, () -> userService.updateAccount(request).block());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetAccountById_Success() {
        UUID userId = UUID.randomUUID();
        User existingUser = new User(userId, "John", "Doe", "john.doe@example.com", "Brief description", "City", LocalDate.of(1990, 1, 1), "logoUrl");
        when(userRepository.findById(userId)).thenReturn(Mono.just(existingUser));

        Mono<GetUserInfoResponse> result = userService.getAccountById(userId);

        GetUserInfoResponse response = result.block();
        assertEquals(userId, response.userid());
        assertEquals("John", response.name());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetAccountById_UserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Mono.empty());

        assertThrows(UserAccountNotFoundException.class, () -> userService.getAccountById(userId).block());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testDeleteAccountById_Success() {
        UUID userId = UUID.randomUUID();
        User existingUser = new User(userId, "John", "Doe", "john.doe@example.com", "Brief description", "City", LocalDate.of(1990, 1, 1), "logoUrl");
        when(userRepository.findById(userId)).thenReturn(Mono.just(existingUser));
        when(userRepository.deleteById(userId)).thenReturn(Mono.empty());

        Mono<Void> result = userService.deleteAccountById(userId);

        result.block(); // Just to trigger the deletion
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testDeleteAccountById_UserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Mono.empty());

        assertThrows(UserAccountNotFoundException.class, () -> userService.deleteAccountById(userId).block());
        verify(userRepository, times(1)).findById(userId);
    }
}