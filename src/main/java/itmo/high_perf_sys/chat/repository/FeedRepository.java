package itmo.high_perf_sys.chat.repository;

import itmo.high_perf_sys.chat.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserId(Long userId);
}
