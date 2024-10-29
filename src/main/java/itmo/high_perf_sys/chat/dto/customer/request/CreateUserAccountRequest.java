package itmo.high_perf_sys.chat.dto.customer.request;


import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateUserAccountRequest (
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