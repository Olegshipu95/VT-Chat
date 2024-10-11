package itmo.high_perf_sys.chat.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "follows")
public class Follows {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
