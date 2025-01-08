package chatcore.chat.entity;

public enum ChatType {
    PAIRED("PAIRED"),
    GROUP("GROUP");

    String typeName;

    ChatType(String aChatType) {
        typeName = aChatType;
    }
}
