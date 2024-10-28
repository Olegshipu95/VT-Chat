package itmo.high_perf_sys.chat.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sets_of_stickers")
public class SetOfStickers {
    @Id
    private UUID id;
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "stickers_ids")
    private List<Long> stickers;
}
