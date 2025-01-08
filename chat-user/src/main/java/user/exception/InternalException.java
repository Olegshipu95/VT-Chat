package user.exception;

import org.springframework.http.HttpStatus;

public class InternalException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final ErrorCode errorCode;

    public InternalException() {
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.errorCode = ErrorCode.SERVICE_UNAVAILABLE;
    }

    public InternalException(HttpStatus httpStatus, ErrorCode errorCode) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}