package itmo.high_perf_sys.chat.dto.chat.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateChatRequest {
    @NotNull(message = "chatType cannot be null")
    @Min(value = 0, message = "chatType has not this meaning")
    @Max(value = 1, message = "chatType has not this meaning")
    private int chatType;
    private String name;
    private List<Long> users;

    public int getChatType() {
        return chatType;
    }

    public List<Long> getUsers() {
        return users;
    }

    public String getName() {
        return name;
    }
}
