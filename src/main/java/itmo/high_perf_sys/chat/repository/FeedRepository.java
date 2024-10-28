package itmo.high_perf_sys.chat.repository;

import itmo.high_perf_sys.chat.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.user.id = :userId ORDER BY p.postedTime DESC")
    Page<Post> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
