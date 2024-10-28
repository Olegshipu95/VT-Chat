package itmo.high_perf_sys.chat.repository;

import itmo.high_perf_sys.chat.entity.Subscribers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubRepository extends JpaRepository<Subscribers, UUID> {

}
