package itmo.high_perf_sys.chat.exception;

import java.util.UUID;

public class UserAccountWasNotInsertException extends NotFoundException {

    public UserAccountWasNotInsertException(UUID id) {
        super("User Account was not updated=" + id);
    }
}
