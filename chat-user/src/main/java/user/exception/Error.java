package user.exception;

import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class Error {
    private final String errorId = UUID.randomUUID().toString();
    private final int status;
    private final ErrorCode code;

    public Error(int status, ErrorCode code) {
        this.status = status;
        this.code = code;
    }

    public String getErrorId() {
        return errorId;
    }

    public int getStatus() {
        return status;
    }

    public ErrorCode getCode() {
        return code;
    }

    public ResponseEntity<Error> asResponseEntity() {
        return ResponseEntity.status(this.status).body(this);
    }
}