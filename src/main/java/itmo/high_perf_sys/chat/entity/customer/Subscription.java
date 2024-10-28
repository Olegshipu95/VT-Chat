package itmo.high_perf_sys.chat.entity.customer;

import itmo.high_perf_sys.chat.utils.ErrorMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
    @Min(value = 0, message = ErrorMessages.ID_CANNOT_BE_NEGATIVE)
    private UUID id;

    @Column(name = "user_id")
    @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
    @Min(value = 0, message = ErrorMessages.ID_CANNOT_BE_NEGATIVE)
    private UUID userId;

    @Column(name = "subscribed_user_id")
    @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
    @Min(value = 0, message = ErrorMessages.ID_CANNOT_BE_NEGATIVE)
    private UUID subscribedUserId;

    @Column(name = "subscription_time")
    @NotNull(message = "subscription time can not be null")
    private LocalDateTime subscriptionTime;
}
