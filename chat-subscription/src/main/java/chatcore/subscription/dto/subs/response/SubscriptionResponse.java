package chatcore.subscription.dto.subs.response;

import chatcore.subscription.utils.ErrorMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record SubscriptionResponse (
        @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
        UUID id,

        @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
        @JsonProperty("subscribed_user_id")
        UUID subscribedUserId,

        @NotNull(message = "subscription time can not be null")
        @JsonProperty("subscription_time")
        LocalDateTime subscriptionTime
) {
}