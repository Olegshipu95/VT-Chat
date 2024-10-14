package itmo.high_perf_sys.chat.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users_chats")
public class UsersChats {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Lob
    @Column(name = "chats_ids")
    private List<Long> chats;
}
