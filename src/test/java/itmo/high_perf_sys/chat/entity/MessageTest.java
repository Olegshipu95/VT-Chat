package itmo.high_perf_sys.chat.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageTest {

    private Validator validator;
    private Message message;
    private Chat chat;
    private User user;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        chat = new Chat();
        chat.setId(UUID.randomUUID());
        chat.setName("Test Chat");
        chat.setChatType(0);

        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Test User");

        message = new Message();
        message.setId(UUID.randomUUID());
        message.setChatId(chat);
        message.setAuthorId(user);
        message.setText("Test Message");
        message.setTimestamp(new Timestamp(System.currentTimeMillis()));
        message.setPhoto(new byte[0]);
    }

    @Test
    public void testGettersAndSetters() {
        UUID id = UUID.randomUUID();
        String text = "Test Message";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        byte[] photo = new byte[0];

        message.setId(id);
        message.setChatId(chat);
        message.setAuthorId(user);
        message.setText(text);
        message.setTimestamp(timestamp);
        message.setPhoto(photo);

        assertThat(message.getId()).isEqualTo(id);
        assertThat(message.getChatId()).isEqualTo(chat);
        assertThat(message.getAuthorId()).isEqualTo(user);
        assertThat(message.getText()).isEqualTo(text);
        assertThat(message.getTimestamp()).isEqualTo(timestamp);
        assertThat(message.getPhoto()).isEqualTo(photo);
    }

    @Test
    public void testValidationSuccess() {
        Set<ConstraintViolation<Message>> violations = validator.validate(message);

        assertThat(violations).isEmpty();
    }

    @Test
    public void testValidationFailureChatIdNull() {
        message.setChatId(null);

        Set<ConstraintViolation<Message>> violations = validator.validate(message);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Chat cannot be null");
    }

    @Test
    public void testValidationFailureAuthorIdNull() {
        message.setAuthorId(null);

        Set<ConstraintViolation<Message>> violations = validator.validate(message);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("User cannot be null");
    }

    @Test
    public void testValidationFailureTextNull() {
        message.setText(null);

        Set<ConstraintViolation<Message>> violations = validator.validate(message);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Text cannot be null");
    }
}
