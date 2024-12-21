package chatcore.news.repository;


import chatcore.news.entity.Post;
import chatcore.news.entity.Subscribers;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface NewsRepository extends R2dbcRepository<Post, UUID> {
    @Query("SELECT p FROM Post p WHERE p.user.id IN (SELECT s.userId FROM Subscribers s WHERE s.userId = :currentUserId)")
    Flux<Post> findPostsBySubscriber(@Param("currentUserId") UUID currentUserId);
}

