package chatcore.chat.dto.chat.response;


import chatcore.chat.entity.ChatType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ChatForResponse {
    UUID id;
    ChatType chatType;
    int countMembers;
    String lastMessage;
    boolean lastMessageHavePhoto;


}
