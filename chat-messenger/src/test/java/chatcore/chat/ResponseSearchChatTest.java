package chatcore.chat;

import chatcore.chat.dto.chat.response.ChatForResponse;
import chatcore.chat.dto.chat.response.ResponseSearchChat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class ResponseSearchChatTest {

    @Test
    public void testSetAndGetResponse() {
        // Создаем объект ResponseSearchChat
        ResponseSearchChat responseSearchChat = new ResponseSearchChat();

        // Создаем список ChatForResponse для тестирования
        ChatForResponse chat1 = new ChatForResponse();
        ChatForResponse chat2 = new ChatForResponse();
        List<ChatForResponse> chatList = Arrays.asList(chat1, chat2);

        // Устанавливаем список в объект ResponseSearchChat
        responseSearchChat.setResponse(chatList);

        // Проверяем, что список был установлен корректно
        List<ChatForResponse> result = responseSearchChat.getResponse();
        assertEquals(chatList, result);
    }

    @Test
    public void testGetResponseWhenNull() {
        // Создаем объект ResponseSearchChat
        ResponseSearchChat responseSearchChat = new ResponseSearchChat();

        // Проверяем, что getResponse возвращает null, если response не был установлен
        assertNull(responseSearchChat.getResponse());
    }
}
