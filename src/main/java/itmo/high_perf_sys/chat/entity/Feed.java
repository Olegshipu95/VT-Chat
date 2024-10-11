package itmo.high_perf_sys.chat.entity;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "text", nullable = false)
    private String text;
    @Lob
    @Column(name = "images")
    private List<byte[]> images;
    @Column(name = "posted_time")
    private Timestamp postedTime;
}
