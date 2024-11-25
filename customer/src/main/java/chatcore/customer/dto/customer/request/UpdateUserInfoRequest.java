package chatcore.customer.dto.customer.request;

import chatcore.customer.utils.ErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateUserInfoRequest(
        @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
        UUID userId,
        @NotBlank(message = "Name can't be blank")
        String name,
        @NotBlank(message = "Surname can't be blank")
        String surname,
        @NotBlank(message = "Email can't be blank")
        String email,
        String briefDescription,
        String city,
        LocalDate birthday,
        String logoUrl
) {
}