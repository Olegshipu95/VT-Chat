package itmo.high_perf_sys.chat.repository;

import itmo.high_perf_sys.chat.entity.Chat;
import itmo.high_perf_sys.chat.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
}
