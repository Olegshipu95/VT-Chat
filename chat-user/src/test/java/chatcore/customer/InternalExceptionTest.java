package chatcore.customer;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import user.exception.ErrorCode;
import user.exception.InternalException;

import static org.junit.jupiter.api.Assertions.*;

class InternalExceptionTest {

    @Test
    void testDefaultConstructor() {
        InternalException exception = new InternalException();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus());
        assertEquals(ErrorCode.SERVICE_UNAVAILABLE, exception.getErrorCode());
    }

    @Test
    void testParameterizedConstructor() {
        HttpStatus customStatus = HttpStatus.BAD_REQUEST;
        ErrorCode customErrorCode = ErrorCode.INVALID_REQUEST;

        InternalException exception = new InternalException(customStatus, customErrorCode);
        assertEquals(customStatus, exception.getHttpStatus());
        assertEquals(customErrorCode, exception.getErrorCode());
    }

    @Test
    void testGetHttpStatus() {
        InternalException exception = new InternalException(HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN);
        assertEquals(HttpStatus.FORBIDDEN, exception.getHttpStatus());
    }

    @Test
    void testGetErrorCode() {
        InternalException exception = new InternalException(HttpStatus.NOT_FOUND, ErrorCode.USER_NOT_FOUND);
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }
}