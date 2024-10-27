package itmo.high_perf_sys.chat.dto.chat.response;

import java.util.List;

public class ResponseSearchMessage {
    List<MessageForResponse> listOfMessages;

    public ResponseSearchMessage(List<MessageForResponse> listOfMessages) {
        this.listOfMessages = listOfMessages;
    }
}
