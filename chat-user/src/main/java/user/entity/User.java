package user.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import user.utils.ErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class User {

    @Id
    @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
    @Column("id")
    private UUID id;

    @NotBlank(message = "Name can't be blank")
    @Column("name")
    private String name;

    @NotBlank(message = "Surname can't be blank")
    @Column("surname")
    private String surname;

    @NotBlank(message = ErrorMessages.EMAIL_CANNOT_BE_NULL)
    @Column("email")
    private String email;

    @Column("brief_description")
    private String briefDescription;

    @Column("city")
    private String city;

    @Column("birthday")
    private LocalDate birthday;

    @Column("logo_url")
    private String logoUrl;
}
