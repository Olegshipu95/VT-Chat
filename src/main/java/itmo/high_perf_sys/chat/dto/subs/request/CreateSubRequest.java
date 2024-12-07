package itmo.high_perf_sys.chat.dto.subs.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.high_perf_sys.chat.utils.ErrorMessages;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateSubRequest(

        @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
        @JsonProperty("user_id")
        UUID userId,


        @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
        @JsonProperty("subscribed_user_id")
        UUID subscribedUserId
) {
}
