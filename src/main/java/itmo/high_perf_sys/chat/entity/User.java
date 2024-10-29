package itmo.high_perf_sys.chat.entity;

import itmo.high_perf_sys.chat.utils.ErrorMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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

    public User(){

    }

    public User(UUID id, String name, String surname, String email, String briefDescription, String city, LocalDate birthday, String logoUrl){
        this.id = id;
        this.name = name;
        this.email = email;
        this.briefDescription = briefDescription;
        this.city = city;
        this.birthday = birthday;
        this.logoUrl = logoUrl;
    }

    @Id
    @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
    @Column(name = "id")
    private UUID id;

    @NotBlank(message = "Name can't be blank")
    @Column(name = "name")
    private String name;

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
