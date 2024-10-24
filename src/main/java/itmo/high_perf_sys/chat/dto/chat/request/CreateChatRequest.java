package itmo.high_perf_sys.chat.dto.chat.request;

import java.util.List;

public class CreateChatRequest {
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
