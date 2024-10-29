package itmo.high_perf_sys.chat.repository.chat;

import itmo.high_perf_sys.chat.entity.UsersChats;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;
import java.util.UUID;


public interface UsersChatsRepository extends JpaRepository<UsersChats, UUID> {
    Optional<UsersChats> findByUserId(UUID userId);

    @Query("SELECT uc.id FROM UsersChats uc WHERE :chatId MEMBER OF uc.chats")
    List<UUID> findIdsByChatId(@Param("chatId") UUID chatId);

    @Query(value = "SELECT COUNT(id) FROM Users_Chats as uc WHERE :chatId = ANY(users_chats_id)", nativeQuery = true)
    int countByChatId(@Param("chatId") UUID chatId);
}
