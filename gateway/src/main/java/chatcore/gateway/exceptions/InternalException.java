package chatcore.gateway.exceptions;

import org.springframework.http.HttpStatus;

public class InternalException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final ErrorCode errorCode;

    public InternalException() {
        this(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.SERVICE_UNAVAILABLE);
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