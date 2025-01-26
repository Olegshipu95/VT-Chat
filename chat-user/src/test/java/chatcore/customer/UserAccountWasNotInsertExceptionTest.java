package chatcore.customer;

import org.junit.jupiter.api.Test;
import user.exception.NotFoundException;
import user.exception.UserAccountWasNotInsertException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserAccountWasNotInsertExceptionTest {

    @Test
    void testExceptionMessage() {
        UUID testId = UUID.randomUUID();
        UserAccountWasNotInsertException exception = new UserAccountWasNotInsertException(testId);

        // Verify that the exception message is formatted correctly
        String expectedMessage = "User Account was not updated=" + testId;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testExceptionInheritance() {
        UUID testId = UUID.randomUUID();
        UserAccountWasNotInsertException exception = new UserAccountWasNotInsertException(testId);

        // Verify that the exception is an instance of NotFoundException
        assertTrue(exception instanceof NotFoundException);
    }
}