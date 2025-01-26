package chatcore.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.entity.UsersChats;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersChatsTest {

    private UsersChats usersChats;

    @BeforeEach
    public void setUp() {
        usersChats = new UsersChats();
    }

    @Test
    public void testSetAndGetId() {
        UUID id = UUID.randomUUID();
        usersChats.setId(id);
        assertEquals(id, usersChats.getId());
    }

    @Test
    public void testSetAndGetUserId() {
        UUID userId = UUID.randomUUID();
        usersChats.setUserId(userId);
        assertEquals(userId, usersChats.getUserId());
    }

    @Test
    public void testGetChats_ReturnsEmptyList() {
        List<UUID> chats = usersChats.getChats();
        assertEquals(0, chats.size());
    }

    @Test
    public void testSetChats_DoesNotStoreValues() {
        List<UUID> chatIds = List.of(UUID.randomUUID(), UUID.randomUUID());
        usersChats.setChats(chatIds);
        // Проверяем, что метод getChats() все еще возвращает пустой список
        assertEquals(0, usersChats.getChats().size());
    }
}