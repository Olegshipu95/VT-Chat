package chatcore.customer;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;
import user.exception.ErrorCode;
import user.exception.Error;
import user.exception.InternalException;
import user.exception.RestControllerAdvice;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestControllerAdviceTest {

    private final RestControllerAdvice advice = new RestControllerAdvice();

    @Test
    void testDefaultHandler() {
        Exception ex = new Exception("Some error");
        ResponseEntity<Error> response = advice.defaultHandler(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
        assertEquals(ErrorCode.SERVICE_UNAVAILABLE, response.getBody().getCode());
    }

    @Test
    void testInternalExceptionHandler() {
        InternalException ex = new InternalException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_REQUEST);
        ResponseEntity<Error> response = advice.internalException(ex);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals(ErrorCode.INVALID_REQUEST, response.getBody().getCode());
    }

    @Test
    void testJwtExceptionHandler() {
        JwtException ex = Mockito.mock(JwtException.class);
        ResponseEntity<Error> response = advice.jwtException(ex);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals(ErrorCode.TOKEN_INCORRECT_FORMAT, response.getBody().getCode());
    }

    @Test
    void testValidationExceptionsHandler() {
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Mockito.when(bindingResult.getAllErrors()).thenReturn(List.of(new ObjectError("objectName", "error message")));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
        ResponseEntity<Error> response = advice.validationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals(ErrorCode.INVALID_REQUEST, response.getBody().getCode());
    }

    @Test
    void testAccessDeniedExceptionHandler() {
        AccessDeniedException ex = new AccessDeniedException("Access denied");
        ResponseEntity<Error> response = advice.authorizationDeniedException(ex);

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getBody().getStatus());
        assertEquals(ErrorCode.FORBIDDEN, response.getBody().getCode());
    }
}