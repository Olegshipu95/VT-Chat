package subscription.entity;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import subscription.utils.ErrorMessages;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;


@Table(name = "subscribers")
public class Subscribers {
    @Id
    @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
    private UUID id;

    @Column("user_id")
    @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
    private UUID userId;

    @Column("subscribed_user_id")
    @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
    private UUID subscribedUserId;

    @Column("subscription_time")
    @NotNull(message = "subscription time can not be null")
    private LocalDateTime subscriptionTime;
}
