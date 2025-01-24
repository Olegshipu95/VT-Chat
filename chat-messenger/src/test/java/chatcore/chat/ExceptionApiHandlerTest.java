package chatcore.chat;

import chatcore.chat.exception.ExceptionApiHandler;
import chatcore.chat.exception.UserAccountNotFoundException;
import chatcore.chat.exception.UserAccountWasNotInsertException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.ErrorMessage;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExceptionApiHandlerTest {

    @InjectMocks
    private ExceptionApiHandler exceptionApiHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleUserAccountNotFound() {
        UUID userId = UUID.randomUUID();

        UserAccountNotFoundException ex = new UserAccountNotFoundException(userId);

        ResponseEntity<ErrorMessage> response = exceptionApiHandler.handleUserAccountNotFound(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorMessage errorMessage = response.getBody();
        assertNotNull(errorMessage);
        assertEquals("chatcore.chat.exception.UserAccountNotFoundException: " + ex.getMessage(), errorMessage.getPayload().toString());
    }

    @Test
    public void testHandleUserAccountWasNotInserted() {
        UUID userId = UUID.randomUUID();
        UserAccountWasNotInsertException ex = new UserAccountWasNotInsertException(userId);

        ResponseEntity<ErrorMessage> response = exceptionApiHandler.handleUserAccountWasNotInserted(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorMessage errorMessage = response.getBody();
        assertNotNull(errorMessage);
        assertEquals("chatcore.chat.exception.UserAccountWasNotInsertException: " + ex.getMessage(), errorMessage.getPayload().toString());
    }
}
