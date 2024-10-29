package itmo.high_perf_sys.chat.repository.chat;


import itmo.high_perf_sys.chat.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository  extends JpaRepository<Message, UUID> {
    @Query("SELECT m FROM Message m WHERE m.chatId.id = :chatId ORDER BY m.timestamp DESC")
    Optional<Message> findLastByChatId(@Param("chatId") UUID chatId);

    @Query("SELECT m FROM Message m WHERE m.chatId.id = :chatId ORDER BY m.timestamp DESC")
    Page<Message> findByChatId(@Param("chatId") UUID chatId, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.chatId = :chatId AND m.text LIKE %:request%")
    Page<Message> findByTextContainingAndChatId(@Param("chatId") UUID chatId, @Param("request") String request, Pageable pageable);

}
