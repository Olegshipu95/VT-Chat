package itmo.high_perf_sys.chat.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users_chats")
public class UsersChats {
    @Id
    private UUID id;
    @Column(name = "user_id")
    private UUID userId;
    @ElementCollection
    @CollectionTable(name = "users_chats_chats", joinColumns = @JoinColumn(name = "users_chats_id"))
    @Column(name = "chats_ids")
    private List<UUID> chats;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<UUID> getChats() {
        return chats;
    }

    public void setChats(List<UUID> chats) {
        this.chats = chats;
    }
}
