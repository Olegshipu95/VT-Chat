package user.exception;

public enum ErrorCode {
    // common errors
    INVALID_REQUEST,
    SERVICE_UNAVAILABLE,

    // jwt errors
    TOKEN_EXPIRED,
    TOKEN_DOES_NOT_EXIST,
    TOKEN_INCORRECT_FORMAT,

    // user errors
    FORBIDDEN,
    USER_NOT_FOUND,
    USER_ALREADY_EXIST,
    USER_PASSWORD_INCORRECT,
    ROLE_NOT_FOUND
}