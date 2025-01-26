package chatcore.chat.dto.chat.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ResponseSearchChat {
    public List<ChatForResponse> response;

    public void setResponse(List<ChatForResponse> response) {
        this.response = response;
    }

    public List<ChatForResponse> getResponse() {
        return response;
    }
}
