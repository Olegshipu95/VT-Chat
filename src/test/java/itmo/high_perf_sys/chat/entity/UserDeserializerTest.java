package itmo.high_perf_sys.chat.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import itmo.high_perf_sys.chat.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDeserializerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private UserDeserializer userDeserializer;

    private User user;
    private JsonParser jsonParser;
    private DeserializationContext deserializationContext;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Test User");
        user.setSurname("Test Surname");
        user.setEmail("test@example.com");

        jsonParser = mock(JsonParser.class);
        deserializationContext = mock(DeserializationContext.class);
    }

    @Test
    public void testDeserialize() throws IOException {
        UUID userId = user.getId();
        when(jsonParser.getText()).thenReturn(userId.toString());
        when(customerService.findById(userId)).thenReturn(user);

        User result = userDeserializer.deserialize(jsonParser, deserializationContext);

        assertEquals(user, result);
        verify(customerService, times(1)).findById(userId);
    }

    @Test
    public void testDeserializeWithInvalidId() throws IOException {
        when(jsonParser.getText()).thenReturn("invalid-uuid");

        assertThrows(IllegalArgumentException.class, () -> userDeserializer.deserialize(jsonParser, deserializationContext));

        verify(customerService, never()).findById(any(UUID.class));
    }
}
