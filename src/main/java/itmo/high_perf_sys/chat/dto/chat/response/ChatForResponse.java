package itmo.high_perf_sys.chat.dto.chat.response;

import itmo.high_perf_sys.chat.model.entity.ChatType;

public class ChatForResponse {
    ChatType chatType;
    int countMembers;
    String lastMessage;
    boolean lastMessageHavePhoto;


    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    public void setCountMembers(int countMembers) {
        this.countMembers = countMembers;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setLastMessageHavePhoto(boolean lastMessageHavePhoto) {
        this.lastMessageHavePhoto = lastMessageHavePhoto;
    }
}
