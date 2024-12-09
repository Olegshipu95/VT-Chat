package chatcore.news.repository;


import chatcore.news.entity.Post;
import chatcore.news.entity.User;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.UUID;
import reactor.core.publisher.Flux;

@Repository
public class NewsRepository {
    private final DatabaseClient databaseClient;

    public NewsRepository(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    public Flux<Post> findPostsBySubscriber(UUID currentUserId, int offset, int limit) {
        String query = "SELECT p.* FROM Post p WHERE p.user_id IN (SELECT s.subscribed_user_id FROM Subscribers s WHERE s.user_id = :currentUserId) LIMIT :limit OFFSET :offset";

        return databaseClient.sql(query)
                .bind("currentUserId", currentUserId)
                .bind("limit", limit)
                .bind("offset", offset)
                .map((row, metadata) -> {
                    return new Post(row.get("id", UUID.class), row.get("user", User.class), row.get("title", String.class), row.get("text", String.class), (byte[]) row.get("images"), row.get("posted_time", Timestamp.class));
                })
                .all();
    }
}
