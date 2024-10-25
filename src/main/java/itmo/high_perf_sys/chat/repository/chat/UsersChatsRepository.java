package itmo.high_perf_sys.chat.repository.chat;

import itmo.high_perf_sys.chat.entity.UsersChats;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;


public interface UsersChatsRepository extends JpaRepository<UsersChats, Long> {
    Optional<UsersChats> findByUserId(Long id);

    @Query("SELECT uc.id FROM UsersChats uc WHERE :chatId MEMBER OF uc.chats")
    List<Long> findIdsByChatId(@Param("chatId") Long chatId);

    @Query("SELECT COUNT(uc) FROM UsersChats uc WHERE :chatId MEMBER OF uc.chats")
    int countByChatId(@Param("chatId") Long chatId);
}