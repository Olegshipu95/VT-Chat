package chatcore.chat.dto.chat.response;

import java.util.List;

public class ResponseGettingMessages {
    List<MessageForResponse> response;

    public ResponseGettingMessages(List<MessageForResponse> response) {
        this.response = response;
    }

    public List<MessageForResponse> getResponse() {
        return response;
    }

    public void setResponse(List<MessageForResponse> response) {
        this.response = response;
    }
}
