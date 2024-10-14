package itmo.high_perf_sys.chat.entity;

public enum ChatType {
    PAIRED("PAIRED"),
    GROUP("GROUP");

    String typeName;

    ChatType(String aChatType) {
        typeName = aChatType;
    }
}
