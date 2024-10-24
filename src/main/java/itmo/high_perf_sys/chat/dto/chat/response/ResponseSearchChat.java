package itmo.high_perf_sys.chat.dto.chat.response;

import itmo.high_perf_sys.chat.entity.Chat;

import java.util.List;

public class ResponseSearchChat {
    public List<ChatForResponse> response;

    public void setResponse(List<ChatForResponse> response) {
        this.response = response;
    }
}
