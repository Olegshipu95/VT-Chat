package chatcore.chat;

import chatcore.chat.entity.Chat;
import chatcore.chat.entity.ChatDeserializer;
import chatcore.chat.service.ChatService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ChatDeserializerTest {

    @Mock
    private ChatService chatService;

    @Mock
    private JsonParser jsonParser;

    @Mock
    private DeserializationContext deserializationContext;

    @InjectMocks
    private ChatDeserializer chatDeserializer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeserialize() throws IOException {
        // Создаем тестовые данные
        UUID chatId = UUID.randomUUID();
        Chat chat = new Chat();
        chat.setId(chatId);

        // Настраиваем мок
        when(jsonParser.getText()).thenReturn(chatId.toString());
        when(chatService.findById(any(UUID.class))).thenReturn(chat);

        // Вызываем метод deserialize
        Chat result = chatDeserializer.deserialize(jsonParser, deserializationContext);

        // Проверяем, что метод findById был вызван с правильным UUID
        verify(chatService).findById(chatId);

        // Проверяем, что объект Chat был возвращен корректно
        assertEquals(chatId, result.getId());
    }
}
