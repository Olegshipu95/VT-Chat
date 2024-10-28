package itmo.high_perf_sys.chat.dto.customer.request;

import itmo.high_perf_sys.chat.utils.ErrorMessages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateSubRequest(

        @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
        @Min(value = 0, message = ErrorMessages.ID_CANNOT_BE_NEGATIVE)
        UUID userId,


        @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
        @Min(value = 0, message = ErrorMessages.ID_CANNOT_BE_NEGATIVE)
        UUID subscribedUserId
) {
}
