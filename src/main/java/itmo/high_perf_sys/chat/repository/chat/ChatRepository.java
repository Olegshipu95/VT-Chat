package itmo.high_perf_sys.chat.repository.chat;


import itmo.high_perf_sys.chat.entity.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {
    @Query("SELECT c FROM Chat c WHERE c.id IN :chatIds AND c.name LIKE %:request%")
    Page<Chat> findByNameContainingAndIdIn(@Param("chatIds") List<UUID> chatIds, @Param("request") String request, Pageable pageable);
}
