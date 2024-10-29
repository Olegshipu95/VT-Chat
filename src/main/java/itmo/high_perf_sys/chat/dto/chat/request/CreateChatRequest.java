package itmo.high_perf_sys.chat.dto.chat.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public class CreateChatRequest {
    @NotNull(message = "chatType cannot be null")
    @Min(value = 0, message = "chatType has not this meaning")
    @Max(value = 1, message = "chatType has not this meaning")
    private int chatType;
    private String name;
    private List<UUID> users;

    public int getChatType() {
        return chatType;
    }

    public List<UUID> getUsers() {
        return users;
    }

    public String getName() {
        return name;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsers(List<UUID> users) {
        this.users = users;
    }
}
