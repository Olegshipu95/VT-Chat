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
    @ElementCollection
    @Column(name = "chats_ids")
    private List<Long> chats;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getChats() {
        return chats;
    }

    public void setChats(List<Long> chats) {
        this.chats = chats;
    }
}
