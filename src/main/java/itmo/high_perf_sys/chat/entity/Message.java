package itmo.high_perf_sys.chat.model.entity;

import itmo.high_perf_sys.chat.utils.ErrorMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
    @Min(value = 0, message = ErrorMessages.ID_CANNOT_BE_NEGATIVE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    @NotNull(message = ErrorMessages.CHAT_CANNOT_BE_NULL)
    private Chat chatId;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull(message = ErrorMessages.USER_CANNOT_BE_NULL)
    private User authorId;
    @Column(name = "text", nullable = false)
    @NotNull(message = ErrorMessages.TEXT_CANNOT_BE_NULL)
    private String text;
    @Column(name = "messaged_time")
    private Timestamp timestamp;
    @Lob
    @Column(name = "photo")
    private byte[] photo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
