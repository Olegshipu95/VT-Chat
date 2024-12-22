package chatcore.subscription.exception;

import java.util.UUID;

public class UserAccountNotFoundException extends NotFoundException {

    public UserAccountNotFoundException(UUID id) {
        super("Could not find user account by id=" + id);
    }
}