package subscription.repository;

import subscription.dto.subs.response.SubscriptionResponse;
import subscription.entity.Subscribers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SubRepository extends JpaRepository<Subscribers, UUID> {

    @Query("""
            SELECT new chatcore.subscription.dto.subs.response.SubscriptionResponse(s.id, s.subscribedUserId, s.subscriptionTime)
            FROM Sub s
            WHERE s.userId = :userId
            """)
    List<SubscriptionResponse> getSubResponseByUserId(@Param("userId") UUID userId);
}
