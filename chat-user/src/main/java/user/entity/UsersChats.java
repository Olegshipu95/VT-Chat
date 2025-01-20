package user.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Table(name = "users_chats")
public class UsersChats {
    @Id
    private UUID id;
    @Column("user_id")
    private UUID userId;
//    @ElementCollection
//    @CollectionTable(name = "users_chats_chats", joinColumns = @JoinColumn(name = "users_chats_id"))
//    @Column("chats_ids")
//    private List<UUID> chats;

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

    // TODO: FIX
    public List<UUID> getChats() {
        return List.of();
    }

    // TODO: FIX
    public void setChats(List<UUID> chats) {

    }
}
