package chatcore.customer;

import org.junit.jupiter.api.Test;
import user.exception.ErrorCode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ErrorCodeTest {

    @Test
    public void testErrorCodeValues() {
        // Проверяем, что все значения перечисления доступны
        ErrorCode[] errorCodes = ErrorCode.values();
        assertNotNull(errorCodes);
        assertEquals(11, errorCodes.length); // Проверяем, что количество значений соответствует ожидаемому

        // Проверяем, что каждое значение перечисления соответствует ожидаемым
        assertEquals(ErrorCode.INVALID_REQUEST, ErrorCode.valueOf("INVALID_REQUEST"));
        assertEquals(ErrorCode.SERVICE_UNAVAILABLE, ErrorCode.valueOf("SERVICE_UNAVAILABLE"));
        assertEquals(ErrorCode.TOKEN_EXPIRED, ErrorCode.valueOf("TOKEN_EXPIRED"));
        assertEquals(ErrorCode.TOKEN_DOES_NOT_EXIST, ErrorCode.valueOf("TOKEN_DOES_NOT_EXIST"));
        assertEquals(ErrorCode.TOKEN_INCORRECT_FORMAT, ErrorCode.valueOf("TOKEN_INCORRECT_FORMAT"));
        assertEquals(ErrorCode.FORBIDDEN, ErrorCode.valueOf("FORBIDDEN"));
        assertEquals(ErrorCode.USER_NOT_FOUND, ErrorCode.valueOf("USER_NOT_FOUND"));
        assertEquals(ErrorCode.USER_ALREADY_EXIST, ErrorCode.valueOf("USER_ALREADY_EXIST"));
        assertEquals(ErrorCode.USER_PASSWORD_INCORRECT, ErrorCode.valueOf("USER_PASSWORD_INCORRECT"));
        assertEquals(ErrorCode.ROLE_NOT_FOUND, ErrorCode.valueOf("ROLE_NOT_FOUND"));
    }
}