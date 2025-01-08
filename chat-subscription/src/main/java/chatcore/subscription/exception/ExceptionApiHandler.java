package chatcore.subscription.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(UserAccountNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserAccountNotFound(UserAccountNotFoundException ex) {
        return handleException(ex);
    }

    @ExceptionHandler(UserAccountWasNotInsertException.class)
    public ResponseEntity<ErrorMessage> handleUserAccountWasNotInserted(UserAccountWasNotInsertException ex) {
        return handleException(ex);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<String> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The provided ID is null or invalid: " + ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid argument: " + ex.getMessage());
    }

    private ResponseEntity<ErrorMessage> handleException(Exception ex) {
        log.info("Bad_Request: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex));
    }
}
