package chatcore.news.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import chatcore.news.utils.ErrorMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscribers")
public class Subscribers {
    @Id
    @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
    private UUID id;

    @Column(name = "user_id")
    @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
    private UUID userId;

    @Column(name = "subscribed_user_id")
    @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
    private UUID subscribedUserId;

    @Column(name = "subscription_time")
    @NotNull(message = "subscription time can not be null")
    private LocalDateTime subscriptionTime;
}