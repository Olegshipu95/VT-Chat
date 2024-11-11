package itmo.high_perf_sys.chat.customer;

import itmo.high_perf_sys.chat.dto.customer.request.CreateUserAccountRequest;
import itmo.high_perf_sys.chat.dto.customer.request.UpdateUserInfoRequest;
import itmo.high_perf_sys.chat.dto.customer.response.GetUserInfoResponse;
import itmo.high_perf_sys.chat.entity.User;
import itmo.high_perf_sys.chat.repository.UserRepository;
import itmo.high_perf_sys.chat.repository.chat.UsersChatsRepository;
import itmo.high_perf_sys.chat.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Disabled
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsersChatsRepository usersChatsRepository;

    @Test
    void createAccount_shouldCreateAndReturnUserId() {
        CreateUserAccountRequest request = new CreateUserAccountRequest("Jane", "Doe", "janedoe@example.com", "Brief", "City", null, "url");

        UUID userId = userService.createAccount(request);

        assertNotNull(userId);
        assertTrue(userRepository.existsById(userId));
    }

    @Test
    void updateAccount_shouldUpdateExistingAccount() {
        CreateUserAccountRequest createRequest = new CreateUserAccountRequest("Jane", "Doe", "janedoe@example.com", "Brief", "City", null, "url");
        UUID userId = userService.createAccount(createRequest);

        UpdateUserInfoRequest updateRequest = new UpdateUserInfoRequest(userId, "Jane", "Smith", "janesmith@example.com", "Updated Brief", "New City", null, "new-url");
        UUID updatedUserId = userService.updateAccount(updateRequest);

        assertEquals(userId, updatedUserId);
        User updatedUser = userRepository.findUserAccountById(userId);
        assertEquals("Jane", updatedUser.getName());
        assertEquals("Smith", updatedUser.getSurname());
    }

    @Test
    void getAccountById_shouldReturnAccountInfo_whenAccountExists() {
        CreateUserAccountRequest createRequest = new CreateUserAccountRequest("Jane", "Doe", "janedoe@example.com", "Brief", "City", null, "url");
        UUID userId = userService.createAccount(createRequest);

        GetUserInfoResponse response = userService.getAccountById(userId);

        assertEquals(userId, response.userid());
        assertEquals("Jane", response.name());
        assertEquals("Doe", response.surname());
    }

    @Test
    void deleteAccountById_shouldDeleteAccount() {
        CreateUserAccountRequest createRequest = new CreateUserAccountRequest("Jane", "Doe", "janedoe@example.com", "Brief", "City", null, "url");
        UUID userId = userService.createAccount(createRequest);

        userService.deleteAccountById(userId);

        assertFalse(userRepository.existsById(userId));
    }
}