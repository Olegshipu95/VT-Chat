package chatcore.chat;

import chatcore.chat.dto.customer.response.GetUserInfoResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GetUserInfoResponseTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testGetUserInfoResponseInitialization() {
        // Создаем тестовые данные
        UUID userId = UUID.randomUUID();
        String name = "John";
        String surname = "Doe";
        String email = "john.doe@example.com";
        String briefDescription = "A brief description";
        String city = "New York";
        LocalDate birthday = LocalDate.of(1990, 1, 1);
        String logoUrl = "http://example.com/logo.png";

        // Создаем объект GetUserInfoResponse
        GetUserInfoResponse userInfoResponse = new GetUserInfoResponse(
                userId, name, surname, email, briefDescription, city, birthday, logoUrl
        );

        // Проверяем, что все поля были инициализированы корректно
        assertEquals(userId, userInfoResponse.userid());
        assertEquals(name, userInfoResponse.name());
        assertEquals(surname, userInfoResponse.surname());
        assertEquals(email, userInfoResponse.email());
        assertEquals(briefDescription, userInfoResponse.briefDescription());
        assertEquals(city, userInfoResponse.city());
        assertEquals(birthday, userInfoResponse.birthday());
        assertEquals(logoUrl, userInfoResponse.logoUrl());
    }

    @Test
    public void testGetUserInfoResponseValidation() {
        // Проверяем, что поля с аннотациями @NotNull и @NotBlank не могут быть null или пустыми
        GetUserInfoResponse invalidUserInfoResponse = new GetUserInfoResponse(
                null, "", "", "", "A brief description", "New York", LocalDate.of(1990, 1, 1), "http://example.com/logo.png"
        );

        Set<ConstraintViolation<GetUserInfoResponse>> violations = validator.validate(invalidUserInfoResponse);
        assertFalse(violations.isEmpty());

        for (ConstraintViolation<GetUserInfoResponse> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}
