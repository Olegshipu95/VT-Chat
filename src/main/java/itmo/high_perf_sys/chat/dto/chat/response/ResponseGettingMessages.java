package itmo.high_perf_sys.chat.dto.chat.response;

import java.util.List;

public class ResponseGettingMessages {
    List<MessageForResponse> response;

    public ResponseGettingMessages(List<MessageForResponse> response) {
        this.response = response;
    }
}
