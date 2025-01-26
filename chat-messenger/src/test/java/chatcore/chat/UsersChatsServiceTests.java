package chatcore.chat;

import chatcore.chat.entity.UsersChats;
import chatcore.chat.repository.chat.UsersChatsRepository;
import chatcore.chat.service.UsersChatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UsersChatsServiceTests {

    @Mock
    private UsersChatsRepository usersChatsRepository;

    @InjectMocks
    private UsersChatsService usersChatsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByUserId() {
        UUID userId = UUID.randomUUID();
        UsersChats usersChats = new UsersChats();
        when(usersChatsRepository.findByUserId(userId)).thenReturn(Optional.of(usersChats));

        Optional<UsersChats> result = usersChatsService.findByUserId(userId);

        assertEquals(Optional.of(usersChats), result);
        verify(usersChatsRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void testSave() {
        UsersChats usersChats = new UsersChats();
        when(usersChatsRepository.save(usersChats)).thenReturn(usersChats);

        UsersChats result = usersChatsService.save(usersChats);

        assertEquals(usersChats, result);
        verify(usersChatsRepository, times(1)).save(usersChats);
    }

    @Test
    public void testFindIdsByChatId() {
        UUID chatId = UUID.randomUUID();
        List<UUID> userIds = new ArrayList<>();
        userIds.add(UUID.randomUUID());
        when(usersChatsRepository.findIdsByChatId(chatId)).thenReturn(userIds);

        List<UUID> result = usersChatsService.findIdsByChatId(chatId);

        assertEquals(userIds, result);
        verify(usersChatsRepository, times(1)).findIdsByChatId(chatId);
    }

    @Test
    public void testFindById() {
        UUID id = UUID.randomUUID();
        UsersChats usersChats = new UsersChats();
        when(usersChatsRepository.findById(id)).thenReturn(Optional.of(usersChats));

        Optional<UsersChats> result = usersChatsService.findById(id);

        assertEquals(Optional.of(usersChats), result);
        verify(usersChatsRepository, times(1)).findById(id);
    }

    @Test
    public void testCountByChatId() {
        UUID chatId = UUID.randomUUID();
        int count = 5;
        when(usersChatsRepository.countByChatId(chatId)).thenReturn(count);

        int result = usersChatsService.countByChatId(chatId);

        assertEquals(count, result);
        verify(usersChatsRepository, times(1)).countByChatId(chatId);
    }
}
