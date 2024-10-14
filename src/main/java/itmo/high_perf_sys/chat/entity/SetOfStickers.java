package itmo.high_perf_sys.chat.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "sets_of_stickers")
public class SetOfStickers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "stickers")
    private List<Sticker> stickers;
}
