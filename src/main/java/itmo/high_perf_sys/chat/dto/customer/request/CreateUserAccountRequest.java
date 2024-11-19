package itmo.high_perf_sys.chat.dto.customer.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateUserAccountRequest (
        @NotBlank(message = "Name can't be blank")
        @JsonProperty("name")
        String name,
        @NotBlank(message = "Surname can't be blank")
        @JsonProperty("surname")
        String surname,
        @NotBlank(message = "Email can't be blank")
        @JsonProperty("email")
        String email,
        @JsonProperty("brief_description")
        String briefDescription,
        @JsonProperty("city")
        String city,
        @JsonProperty("birthday")
        LocalDate birthday,
        @JsonProperty("logo_url")
        String logoUrl
) {
}