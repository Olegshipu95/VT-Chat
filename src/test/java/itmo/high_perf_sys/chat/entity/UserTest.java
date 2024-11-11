package itmo.high_perf_sys.chat.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    private Validator validator;
    private User user;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Test Name");
        user.setSurname("Test Surname");
        user.setEmail("test@example.com");
        user.setBriefDescription("Test Description");
        user.setCity("Test City");
        user.setBirthday(LocalDate.now());
        user.setLogoUrl("http://example.com/logo.png");
    }

    @Test
    public void testGettersAndSetters() {
        UUID id = UUID.randomUUID();
        String name = "Test Name";
        String surname = "Test Surname";
        String email = "test@example.com";
        String briefDescription = "Test Description";
        String city = "Test City";
        LocalDate birthday = LocalDate.now();
        String logoUrl = "http://example.com/logo.png";

        user.setId(id);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setBriefDescription(briefDescription);
        user.setCity(city);
        user.setBirthday(birthday);
        user.setLogoUrl(logoUrl);

        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getSurname()).isEqualTo(surname);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getBriefDescription()).isEqualTo(briefDescription);
        assertThat(user.getCity()).isEqualTo(city);
        assertThat(user.getBirthday()).isEqualTo(birthday);
        assertThat(user.getLogoUrl()).isEqualTo(logoUrl);
    }

    @Test
    public void testValidationSuccess() {
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).isEmpty();
    }

    @Test
    public void testValidationFailureIdNull() {
        user.setId(null);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Id cannot be null");
    }

    @Test
    public void testValidationFailureNameBlank() {
        user.setName("");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Name can't be blank");
    }

    @Test
    public void testValidationFailureSurnameBlank() {
        user.setSurname("");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Surname can't be blank");
    }

    @Test
    public void testValidationFailureEmailBlank() {
        user.setEmail("");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Email cannot be blank");
    }
}
