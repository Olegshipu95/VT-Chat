package chatcore.gateway.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import org.springframework.web.server.ServerWebInputException;

@ControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> defaultHandler(Exception ex) {
        return new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorCode.SERVICE_UNAVAILABLE)
                .asResponseEntity();
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<Error> internalException(InternalException ex) {
        return new Error(ex.getHttpStatus().value(), ex.getErrorCode())
                .asResponseEntity();
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Error> resourcesNotFoundExceptions(Exception exception) {
        return new Error(HttpStatus.NOT_FOUND.value(), ErrorCode.RESOURCES_NOT_FOUND)
                .asResponseEntity();
    }

    @ExceptionHandler({
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            WebExchangeBindException.class,
            ServerWebInputException.class
    })
    public ResponseEntity<Error> validationExceptions(Exception exception) {
        return new Error(HttpStatus.BAD_REQUEST.value(), ErrorCode.INVALID_REQUEST)
                .asResponseEntity();
    }
}