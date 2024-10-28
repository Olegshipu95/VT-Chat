package itmo.high_perf_sys.chat.dto.chat.response;

import itmo.high_perf_sys.chat.model.entity.Chat;
import itmo.high_perf_sys.chat.model.entity.Message;
import itmo.high_perf_sys.chat.model.entity.User;

import java.sql.Timestamp;

public class MessageForResponse {
    private Long id;
    private Chat chatId;
    private User authorId;
    private String text;
    private Timestamp timestamp;
    private byte[] photo;

    public void setChatId(Chat chatId) {
        this.chatId = chatId;
    }

    public void setAuthorId(User authorId) {
        this.authorId = authorId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MessageForResponse(Message message) {
        this.id = message.getId();
        this.chatId = message.getChatId();
        this.authorId = message.getAuthorId();
        this.text = message.getText();
        this.timestamp = message.getTimestamp();
        this.photo = message.getPhoto();
    }
}
