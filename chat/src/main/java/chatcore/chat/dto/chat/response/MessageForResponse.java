package chatcore.chat.dto.chat.response;


import chatcore.chat.entity.Chat;
import chatcore.chat.entity.Message;
import chatcore.chat.entity.User;

import java.sql.Timestamp;
import java.util.UUID;

public class MessageForResponse {
    private UUID id;
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

    public void setId(UUID id) {
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

    public UUID getId() {
        return id;
    }

    public Chat getChatId() {
        return chatId;
    }

    public User getAuthorId() {
        return authorId;
    }

    public String getText() {
        return text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public byte[] getPhoto() {
        return photo;
    }
}
