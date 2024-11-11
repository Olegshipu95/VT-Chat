package itmo.high_perf_sys.chat.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import itmo.high_perf_sys.chat.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatDeserializerTest {

    @Mock
    private ChatService chatService;

    @InjectMocks
    private ChatDeserializer chatDeserializer;

    private Chat chat;
    private JsonParser jsonParser;
    private DeserializationContext deserializationContext;

    @BeforeEach
    public void setUp() {
        chat = new Chat();
        chat.setId(UUID.randomUUID());
        chat.setName("Test Chat");
        chat.setChatType(0);

        jsonParser = mock(JsonParser.class);
        deserializationContext = mock(DeserializationContext.class);
    }

    @Test
    public void testDeserialize() throws IOException {
        UUID chatId = chat.getId();
        when(jsonParser.getText()).thenReturn(chatId.toString());
        when(chatService.findById(chatId)).thenReturn(chat);

        Chat result = chatDeserializer.deserialize(jsonParser, deserializationContext);

        assertEquals(chat, result);
        verify(chatService, times(1)).findById(chatId);
    }

    @Test
    public void testDeserializeWithInvalidId() throws IOException {
        when(jsonParser.getText()).thenReturn("invalid-uuid");

        try {
            chatDeserializer.deserialize(jsonParser, deserializationContext);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid UUID string: invalid-uuid", e.getMessage());
        }

        verify(chatService, never()).findById(any(UUID.class));
    }
}
