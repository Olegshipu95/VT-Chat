package chatcore.chat.entity;

import chatcore.chat.utils.ErrorMessages;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
    @Column(name = "id")
    private UUID id;

    @NotBlank(message = "Name can't be blank")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Surname can't be blank")
    @Column(name = "surname")
    private String surname;

    @NotBlank(message = ErrorMessages.EMAIL_CANNOT_BE_NULL)
    @Column(name = "email")
    private String email;

    @Column(name = "brief_description")
    private String briefDescription;

    @Column(name = "city")
    private String city;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "logo_url")
    private String logoUrl;
}
