package user.dto.response;

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
        String briefDescription,
        String city,
        LocalDate birthday,
        String logoUrl
){
}
