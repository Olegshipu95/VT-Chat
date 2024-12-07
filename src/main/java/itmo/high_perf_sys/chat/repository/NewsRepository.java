package itmo.high_perf_sys.chat.repository;

import itmo.high_perf_sys.chat.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<Post, UUID> {
    @Query("SELECT p FROM Post p WHERE p.user.id IN (SELECT s.subscribedUserId FROM Subscribers s WHERE s.userId = :currentUserId)")
    Page<Post> findPostsBySubscriber(@Param("currentUserId") UUID currentUserId, Pageable pageable);
}
