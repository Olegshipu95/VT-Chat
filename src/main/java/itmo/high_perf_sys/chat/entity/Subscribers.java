package itmo.high_perf_sys.chat.model.entity;

import java.util.List;
import jakarta.persistence.*;


@Entity
@Table(name = "subscribers")
public class Subscribers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Lob
    @Column(name = "subscribers_ids")
    private List<Long> subscribers;
}
