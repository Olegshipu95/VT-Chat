package chatcore.chat;

import chatcore.chat.dto.customer.response.GetUserInfoResponse;
import chatcore.chat.entity.User;
import chatcore.chat.entity.UserDeserializer;
import chatcore.chat.service.CustomerServiceClient;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserDeserializerTest {

    @Mock
    private CustomerServiceClient customerServiceClient;

    @Mock
    private JsonParser jsonParser;

    @Mock
    private DeserializationContext deserializationContext;

    @InjectMocks
    private UserDeserializer userDeserializer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeserialize() throws IOException {
        // Создаем тестовые данные
        UUID userId = UUID.randomUUID();
        String name = "John";
        String surname = "Doe";
        String email = "john.doe@example.com";
        String briefDescription = "A brief description";
        String city = "New York";
        LocalDate birthday = LocalDate.of(1990, 1, 1);
        String logoUrl = "http://example.com/logo.png";

        GetUserInfoResponse getUserInfoResponse = new GetUserInfoResponse(
                userId, name, surname, email, briefDescription, city, birthday, logoUrl
        );

        ResponseEntity<GetUserInfoResponse> responseEntity = ResponseEntity.ok(getUserInfoResponse);

        // Настраиваем мок
        when(jsonParser.getText()).thenReturn(userId.toString());
        when(customerServiceClient.getAccountById(any(UUID.class))).thenReturn(Mono.just(responseEntity));

        // Вызываем метод deserialize
        User user = userDeserializer.deserialize(jsonParser, deserializationContext);

        // Проверяем, что метод getAccountById был вызван с правильным UUID
        verify(customerServiceClient).getAccountById(userId);

        // Проверяем, что объект User был создан корректно
        assertEquals(userId, user.getId());
        assertEquals(name, user.getName());
        assertEquals(surname, user.getSurname());
        assertEquals(email, user.getEmail());
        assertEquals(briefDescription, user.getBriefDescription());
        assertEquals(city, user.getCity());
        assertEquals(birthday, user.getBirthday());
        assertEquals(logoUrl, user.getLogoUrl());
    }
}
