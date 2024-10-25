package itmo.high_perf_sys.chat.entity;

import itmo.high_perf_sys.chat.utils.ErrorMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
    @Min(value = 0, message = ErrorMessages.ID_CANNOT_BE_NEGATIVE)
    private Long id;
    @Column(name = "name", nullable = false)
    @NotNull(message = ErrorMessages.NAME_CANNOT_BE_NULL)
    private String name;

    @Column(name = "chat_type", nullable = false)
    @NotNull(message = "chatType cannot be null")
    @Min(value = 0, message = "chatType has not this meaning")
    @Max(value = 1, message = "chatType has not this meaning")
    private int chatType;

    public Chat() {
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public Long getId() {
        return id;
    }

    public int getChatType() {
        return chatType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
