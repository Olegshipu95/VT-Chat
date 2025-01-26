package chatcore.customer;

import user.controller.UserController;
import user.dto.request.CreateUserAccountRequest;
import user.dto.request.UpdateUserInfoRequest;
import user.dto.response.GetUserInfoResponse;
import user.service.UserService;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAccount_Success() {
        CreateUserAccountRequest request = new CreateUserAccountRequest("John", "Doe", "john.doe@example.com", "Brief description", "City", LocalDate.of(1990, 1, 1), "logoUrl");
        UUID accountId = UUID.randomUUID();
        when(userService.createAccount(request)).thenReturn(Mono.just(accountId));

        Mono<ResponseEntity<UUID>> response = userController.createAccount(request);

        assertEquals(HttpStatus.CREATED, response.block().getStatusCode());
        assertEquals(accountId, response.block().getBody());
        verify(userService, times(1)).createAccount(request);
    }

    @Test
    public void testUpdateAccount_Success() {
        UpdateUserInfoRequest request = new UpdateUserInfoRequest(UUID.randomUUID(), "John", "Doe", "john.doe@example.com", "Brief description", "City", LocalDate.of(1990, 1, 1), "logoUrl");
        UUID accountId = UUID.randomUUID();
        when(userService.updateAccount(request)).thenReturn(Mono.just(accountId));

        Mono<ResponseEntity<UUID>> response = userController.updateAccount(request);

        assertEquals(HttpStatus.OK, response.block().getStatusCode());
        assertEquals(accountId, response.block().getBody());
        verify(userService, times(1)).updateAccount(request);
    }

    @Test
    public void testGetAccountById_Success() {
        UUID accountId = UUID.randomUUID();
        GetUserInfoResponse responseBody = new GetUserInfoResponse(accountId, "John", "Doe", "john.doe@example.com", "Brief description", "City", LocalDate.of(1990, 1, 1), "logoUrl");
        when(userService.getAccountById(accountId)).thenReturn(Mono.just(responseBody));

        Mono<ResponseEntity<GetUserInfoResponse>> response = userController.getAccountById(accountId);

        assertEquals(HttpStatus.OK, response.block().getStatusCode());
        assertEquals(responseBody, response.block().getBody());
        verify(userService, times(1)).getAccountById(accountId);
    }

    @Test
    public void testDeleteAccountById_Success() {
        UUID accountId = UUID.randomUUID();
        when(userService.deleteAccountById(accountId)).thenReturn(Mono.empty());

        Mono<ResponseEntity<Void>> response = userController.deleteAccountById(accountId);

        assertEquals(HttpStatus.NO_CONTENT, response.block().getStatusCode());
        verify(userService, times(1)).deleteAccountById(accountId);
    }
}