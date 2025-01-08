package chatcore.subscription.dto.subs.request;

import chatcore.subscription.utils.ErrorMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

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
