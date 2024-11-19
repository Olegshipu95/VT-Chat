package itmo.high_perf_sys.chat.dto.customer.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.high_perf_sys.chat.utils.ErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateUserInfoRequest(
        @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
        @JsonProperty("user_id")
        UUID userId,
        @NotBlank(message = "Name can't be blank")
        @JsonProperty("name")
        String name,
        @NotBlank(message = "Surname can't be blank")
        @JsonProperty("surname")
        String surname,
        @NotBlank(message = "Email can't be blank")
        @JsonProperty("email")
        String email,
        String briefDescription,
        String city,
        LocalDate birthday,
        String logoUrl
) {
}