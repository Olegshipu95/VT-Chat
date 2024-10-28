package itmo.high_perf_sys.chat.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users_stickers")
public class UsersStickers {
    @Id
    private UUID id;
    @Column(name = "user_id")
    private UUID userId;
    @Lob
    @Column(name = "stickers_ids")
    private List<UUID> stickers;
}
