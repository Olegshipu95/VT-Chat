package itmo.high_perf_sys.chat.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "chat_type", nullable = false)
    private int chatType;

    public Chat() {
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public Long getId() {
        return id;
    }

    public int getChatType() {
        return chatType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

