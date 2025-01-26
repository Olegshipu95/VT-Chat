package chatcore.chat;

import chatcore.chat.dto.chat.response.MessageForResponse;
import chatcore.chat.entity.Chat;
import chatcore.chat.entity.Message;
import chatcore.chat.entity.User;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class MessageForResponseTest {

    @Test
    public void testConstructor() {
        // Создаем объект Message для тестирования
        UUID id = UUID.randomUUID();
        Chat chat = new Chat();
        User user = new User();
        String text = "Test message";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        byte[] photo = new byte[]{1, 2, 3};

        Message message = new Message();
        message.setId(id);
        message.setChatId(chat);
        message.setAuthorId(user);
        message.setText(text);
        message.setTimestamp(timestamp);
        message.setPhoto(photo);

        // Создаем объект MessageForResponse с помощью конструктора
        MessageForResponse messageForResponse = new MessageForResponse(message);

        // Проверяем, что все поля были инициализированы корректно
        assertEquals(id, messageForResponse.getId());
        assertEquals(chat, messageForResponse.getChatId());
        assertEquals(user, messageForResponse.getAuthorId());
        assertEquals(text, messageForResponse.getText());
        assertEquals(timestamp, messageForResponse.getTimestamp());
        assertArrayEquals(photo, messageForResponse.getPhoto());
    }

    @Test
    public void testSetAndGetMethods() {
        // Создаем объект MessageForResponse
        MessageForResponse messageForResponse = new MessageForResponse(new Message());

        // Создаем тестовые данные
        UUID id = UUID.randomUUID();
        Chat chat = new Chat();
        User user = new User();
        String text = "Test message";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        byte[] photo = new byte[]{1, 2, 3};

        // Устанавливаем значения полей
        messageForResponse.setId(id);
        messageForResponse.setChatId(chat);
        messageForResponse.setAuthorId(user);
        messageForResponse.setText(text);
        messageForResponse.setTimestamp(timestamp);
        messageForResponse.setPhoto(photo);

        // Проверяем, что значения полей были установлены корректно
        assertEquals(id, messageForResponse.getId());
        assertEquals(chat, messageForResponse.getChatId());
        assertEquals(user, messageForResponse.getAuthorId());
        assertEquals(text, messageForResponse.getText());
        assertEquals(timestamp, messageForResponse.getTimestamp());
        assertArrayEquals(photo, messageForResponse.getPhoto());
    }
}
