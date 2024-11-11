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

public class PostTest {

    private Validator validator;
    private Post post;
    private User user;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Test User");

        post = new Post();
        post.setId(UUID.randomUUID());
        post.setUser(user);
        post.setTitle("Test Title");
        post.setText("Test Text");
        post.setImages(new byte[0]);
        post.setPostedTime(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    public void testGettersAndSetters() {
        UUID id = UUID.randomUUID();
        String title = "Test Title";
        String text = "Test Text";
        byte[] images = new byte[0];
        Timestamp postedTime = new Timestamp(System.currentTimeMillis());

        post.setId(id);
        post.setUser(user);
        post.setTitle(title);
        post.setText(text);
        post.setImages(images);
        post.setPostedTime(postedTime);

        assertThat(post.getId()).isEqualTo(id);
        assertThat(post.getUser()).isEqualTo(user);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getText()).isEqualTo(text);
        assertThat(post.getImages()).isEqualTo(images);
        assertThat(post.getPostedTime()).isEqualTo(postedTime);
    }

    @Test
    public void testValidationSuccess() {
        Set<ConstraintViolation<Post>> violations = validator.validate(post);

        assertThat(violations).isEmpty();
    }

    @Test
    public void testValidationFailureIdNull() {
        post.setId(null);

        Set<ConstraintViolation<Post>> violations = validator.validate(post);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Id cannot be null");
    }

    @Test
    public void testValidationFailureUserNull() {
        post.setUser(null);

        Set<ConstraintViolation<Post>> violations = validator.validate(post);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Chat cannot be null");
    }
}
