package chatcore.chat.entity;

import chatcore.chat.utils.ErrorMessages;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    @NotNull(message = ErrorMessages.CHAT_CANNOT_BE_NULL)
    @JsonDeserialize(using = ChatDeserializer.class)
    private Chat chatId;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull(message = ErrorMessages.USER_CANNOT_BE_NULL)
    @JsonDeserialize(using = UserDeserializer.class)
    private User authorId;
    @Column(name = "text", nullable = false)
    @NotNull(message = ErrorMessages.TEXT_CANNOT_BE_NULL)
    private String text;
    @Column(name = "messaged_time")
    private Timestamp timestamp;
    @Lob
    @Column(name = "photo")
    private byte[] photo;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Chat getChatId() {
        return chatId;
    }

    public void setChatId(Chat chatId) {
        this.chatId = chatId;
    }

    public User getAuthorId() {
        return authorId;
    }

    public void setAuthorId(User authorId) {
        this.authorId = authorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}