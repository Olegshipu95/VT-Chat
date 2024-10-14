package itmo.high_perf_sys.chat.entity;

import jakarta.persistence.*;

import java.lang.reflect.Array;
import java.sql.Timestamp;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Chat ChatId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User authorId;
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "messaged_time")
    private Timestamp timestamp;
    @Lob
    @Column(name = "photo")
    private byte[] photo;
}
