package chatcore.chat.dto.customer.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
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
