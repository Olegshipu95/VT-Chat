package chatcore.chat;

import chatcore.chat.utils.ErrorMessages;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorMessagesTest {

    @Test
    public void testErrorMessages() {
        assertEquals("Id cannot be null", ErrorMessages.ID_CANNOT_BE_NULL);
        assertEquals("Email cannot be blank", ErrorMessages.EMAIL_CANNOT_BE_NULL);
        assertEquals("Id cannot be negative", ErrorMessages.ID_CANNOT_BE_NEGATIVE);
        assertEquals("Page cannot be null", ErrorMessages.PAGE_CANNOT_BE_NULL);
        assertEquals("Page cannot be negative", ErrorMessages.PAGE_CANNOT_BE_NEGATIVE);
        assertEquals("Count page cannot be null", ErrorMessages.COUNT_PAGE_CANNOT_BE_NULL);
        assertEquals("Count page cannot be negative", ErrorMessages.COUNT_PAGE_CANNOT_BE_NEGATIVE);
        assertEquals("Request cannot be null", ErrorMessages.REQUEST_CANNOT_BE_NULL);
        assertEquals("The problem with the database query", ErrorMessages.ERROR_DB_REQUEST);
        assertEquals("Chat cannot be null", ErrorMessages.CHAT_CANNOT_BE_NULL);
        assertEquals("Message cannot be null", ErrorMessages.MESSAGE_CANNOT_BE_NULL);
        assertEquals("Text cannot be null", ErrorMessages.TEXT_CANNOT_BE_NULL);
        assertEquals("User cannot be null", ErrorMessages.USER_CANNOT_BE_NULL);
        assertEquals("Name cannot be null", ErrorMessages.NAME_CANNOT_BE_NULL);
        assertEquals("Post was not found", ErrorMessages.POST_NOT_FOUND);
        assertEquals("Not found", ErrorMessages.NOT_FOUND);
        assertEquals("User has no permission", ErrorMessages.NO_PERMISSION);
        assertEquals("Paired chat cannot contain more than 2 members", ErrorMessages.USER_COUNT_ERROR);
        assertEquals("List of users cannot contain 2 or more identical users", ErrorMessages.USER_DUPLICATED);
    }
}
