package itmo.high_perf_sys.chat.repository;

import itmo.high_perf_sys.chat.dto.subs.response.SubscriptionResponse;
import itmo.high_perf_sys.chat.entity.Subscribers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SubRepository extends JpaRepository<Subscribers, UUID> {

    List<Subscribers> findAllByUserId(UUID userId);

    @Query("""
            SELECT new itmo.high_perf_sys.chat.dto.subs.response.SubscriptionResponse(s.id, s.subscribedUserId, s.subscriptionTime)
            FROM Subscribers s
            WHERE s.userId = :userId
            """)
    List<SubscriptionResponse> getSubResponseByUserId(@Param("userId") UUID userId);
}
