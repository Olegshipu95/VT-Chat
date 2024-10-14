package itmo.high_perf_sys.chat.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "chat_type", nullable = false)
    private Long chatType;
}

