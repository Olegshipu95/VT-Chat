package itmo.high_perf_sys.chat.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "stickers")
public class Sticker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Lob
    @Column(name = "sticker_picture")
    private byte[] sticker;
    @Column(name = "created_date")
    private Timestamp timestamp;
}
