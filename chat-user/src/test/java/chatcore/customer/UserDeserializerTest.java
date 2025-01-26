package chatcore.customer;

import user.entity.User;
import user.entity.UserDeserializer;
import user.service.UserService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserDeserializerTest {

    @InjectMocks
    private UserDeserializer userDeserializer;

    @Mock
    private UserService userService;

    @Mock
    private JsonParser jsonParser;

    @Mock
    private DeserializationContext deserializationContext;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeserialize_Success() throws IOException {
        UUID userId = UUID.randomUUID();
        User expectedUser = new User(userId, "John", "Doe", "john.doe@example.com", "Brief description", "City", null, null);

        when(jsonParser.getText()).thenReturn(userId.toString());
        when(userService.findById(userId)).thenReturn(Mono.just(expectedUser));

        User actualUser = userDeserializer.deserialize(jsonParser, deserializationContext);

        assertEquals(expectedUser, actualUser);
        verify(userService, times(1)).findById(userId);
    }

    @Test
    public void testDeserialize_UserNotFound() throws IOException {
        UUID userId = UUID.randomUUID();

        when(jsonParser.getText()).thenReturn(userId.toString());
        when(userService.findById(userId)).thenReturn(Mono.empty());

        User actualUser = userDeserializer.deserialize(jsonParser, deserializationContext);

        assertEquals(null, actualUser); // Если пользователь не найден, возвращаем null
        verify(userService, times(1)).findById(userId);
    }

    @Test
    public void testDeserialize_InvalidUUID() throws IOException {
        String invalidUUID = "invalid-uuid";

        when(jsonParser.getText()).thenReturn(invalidUUID);

        assertThrows(IllegalArgumentException.class, () -> userDeserializer.deserialize(jsonParser, deserializationContext));
    }
}