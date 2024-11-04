package itmo.high_perf_sys.chat.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SubscribersTest {

    private Validator validator;
    private Subscribers subscribers;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        subscribers = new Subscribers();
        subscribers.setId(UUID.randomUUID());
        subscribers.setUserId(UUID.randomUUID());
        subscribers.setSubscribedUserId(UUID.randomUUID());
        subscribers.setSubscriptionTime(LocalDateTime.now());
    }

    @Test
    public void testGettersAndSetters() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID subscribedUserId = UUID.randomUUID();
        LocalDateTime subscriptionTime = LocalDateTime.now();

        subscribers.setId(id);
        subscribers.setUserId(userId);
        subscribers.setSubscribedUserId(subscribedUserId);
        subscribers.setSubscriptionTime(subscriptionTime);

        assertThat(subscribers.getId()).isEqualTo(id);
        assertThat(subscribers.getUserId()).isEqualTo(userId);
        assertThat(subscribers.getSubscribedUserId()).isEqualTo(subscribedUserId);
        assertThat(subscribers.getSubscriptionTime()).isEqualTo(subscriptionTime);
    }

    @Test
    public void testValidationSuccess() {
        Set<ConstraintViolation<Subscribers>> violations = validator.validate(subscribers);

        assertThat(violations).isEmpty();
    }

    @Test
    public void testValidationFailureIdNull() {
        subscribers.setId(null);

        Set<ConstraintViolation<Subscribers>> violations = validator.validate(subscribers);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Id cannot be null");
    }

    @Test
    public void testValidationFailureUserIdNull() {
        subscribers.setUserId(null);

        Set<ConstraintViolation<Subscribers>> violations = validator.validate(subscribers);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Id cannot be null");
    }

    @Test
    public void testValidationFailureSubscribedUserIdNull() {
        subscribers.setSubscribedUserId(null);

        Set<ConstraintViolation<Subscribers>> violations = validator.validate(subscribers);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Id cannot be null");
    }

    @Test
    public void testValidationFailureSubscriptionTimeNull() {
        subscribers.setSubscriptionTime(null);

        Set<ConstraintViolation<Subscribers>> violations = validator.validate(subscribers);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("subscription time can not be null");
    }
}
