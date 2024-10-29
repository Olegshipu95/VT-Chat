package itmo.high_perf_sys.chat.dto.chat.response;

import itmo.high_perf_sys.chat.entity.ChatType;

import java.util.UUID;

public class ChatForResponse {
    UUID id;
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

    public ChatType getChatType() {
        return chatType;
    }

    public int getCountMembers() {
        return countMembers;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public boolean isLastMessageHavePhoto() {
        return lastMessageHavePhoto;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
