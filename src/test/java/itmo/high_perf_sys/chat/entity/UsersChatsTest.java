package itmo.high_perf_sys.chat.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UsersChatsTest {

    private UsersChats usersChats;

    @BeforeEach
    public void setUp() {
        usersChats = new UsersChats();
    }

    @Test
    public void testGettersAndSetters() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        List<UUID> chats = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());

        usersChats.setId(id);
        usersChats.setUserId(userId);
        usersChats.setChats(chats);

        assertThat(usersChats.getId()).isEqualTo(id);
        assertThat(usersChats.getUserId()).isEqualTo(userId);
        assertThat(usersChats.getChats()).isEqualTo(chats);
    }

    @Test
    public void testDefaultValues() {
        assertThat(usersChats.getId()).isNull();
        assertThat(usersChats.getUserId()).isNull();
        assertThat(usersChats.getChats()).isNull();
    }
}
