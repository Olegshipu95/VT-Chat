package chatcore.customer;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import user.exception.ErrorCode;
import user.exception.Error;

import static org.junit.jupiter.api.Assertions.*;

public class ErrorTest {

    @Test
    public void testErrorConstructorAndGetters() {
        int status = 404;
        ErrorCode code = ErrorCode.USER_NOT_FOUND; // Используем одно из значений перечисления
        Error error = new Error(status, code);

        assertNotNull(error);
        assertEquals(status, error.getStatus());
        assertEquals(code, error.getCode());
        assertNotNull(error.getErrorId());
    }

    @Test
    public void testAsResponseEntity() {
        int status = 500;
        ErrorCode code = ErrorCode.SERVICE_UNAVAILABLE; // Используем другое значение перечисления
        Error error = new Error(status, code);

        ResponseEntity<Error> responseEntity = error.asResponseEntity();
        assertNotNull(responseEntity);
        assertEquals(status, responseEntity.getStatusCodeValue());
        assertEquals(error, responseEntity.getBody());
    }
}