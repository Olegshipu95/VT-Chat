package itmo.high_perf_sys.chat.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatTest {

    private Validator validator;
    private Chat chat;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        chat = new Chat();
    }

    @Test
    public void testGettersAndSetters() {
        UUID id = UUID.randomUUID();
        String name = "Test Chat";
        int chatType = 0;

        chat.setId(id);
        chat.setName(name);
        chat.setChatType(chatType);

        assertThat(chat.getId()).isEqualTo(id);
        assertThat(chat.getName()).isEqualTo(name);
        assertThat(chat.getChatType()).isEqualTo(chatType);
    }

    @Test
    public void testValidationSuccess() {
        chat.setId(UUID.randomUUID());
        chat.setName("Test Chat");
        chat.setChatType(0);

        Set<ConstraintViolation<Chat>> violations = validator.validate(chat);

        assertThat(violations).isEmpty();
    }

    @Test
    public void testValidationFailureNameNull() {
        chat.setId(UUID.randomUUID());
        chat.setChatType(0);

        Set<ConstraintViolation<Chat>> violations = validator.validate(chat);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Name cannot be null");
    }

    @Test
    public void testValidationFailureChatTypeMin() {
        chat.setId(UUID.randomUUID());
        chat.setName("Test Chat");
        chat.setChatType(-1);

        Set<ConstraintViolation<Chat>> violations = validator.validate(chat);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("chatType has not this meaning");
    }

    @Test
    public void testValidationFailureChatTypeMax() {
        chat.setId(UUID.randomUUID());
        chat.setName("Test Chat");
        chat.setChatType(2);

        Set<ConstraintViolation<Chat>> violations = validator.validate(chat);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("chatType has not this meaning");
    }
}
