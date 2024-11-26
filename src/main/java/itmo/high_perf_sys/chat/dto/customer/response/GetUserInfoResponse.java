package itmo.high_perf_sys.chat.dto.customer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record GetUserInfoResponse (
        @NotNull(message = "Id can't be null")
        UUID userid,
        @NotBlank(message = "Name can't be blank")
        String name,
        @NotBlank(message = "Surname can't be blank")
        String surname,
        @NotBlank(message = "Email can't be blank")
        String email,
        @JsonProperty("brief_description")
        String briefDescription,
        String city,
        LocalDate birthday,
        String logoUrl
){
}
