package itmo.high_perf_sys.chat.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "stickers")
public class Sticker {
    @Id
    private UUID id;
    @Lob
    @Column(name = "sticker_picture")
    private byte[] sticker;
    @Column(name = "created_date")
    private Timestamp timestamp;
}
