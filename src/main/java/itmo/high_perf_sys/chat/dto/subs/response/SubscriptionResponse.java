package itmo.high_perf_sys.chat.dto.subs.response;

import itmo.high_perf_sys.chat.utils.ErrorMessages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record SubscriptionResponse (
        @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
        UUID id,

        @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
        UUID subscribedUserId,

        @NotNull(message = "subscription time can not be null")
        LocalDateTime subscriptionTime
) {
}