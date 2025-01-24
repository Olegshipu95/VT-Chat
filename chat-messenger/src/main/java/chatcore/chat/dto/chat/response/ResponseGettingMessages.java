package chatcore.chat.dto.chat.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ResponseGettingMessages {
    List<MessageForResponse> response;

    public ResponseGettingMessages(List<MessageForResponse> response) {
        this.response = response;
    }

}
