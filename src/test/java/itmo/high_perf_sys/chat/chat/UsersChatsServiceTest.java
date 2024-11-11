package itmo.high_perf_sys.chat.chat;

import itmo.high_perf_sys.chat.entity.UsersChats;
import itmo.high_perf_sys.chat.repository.chat.UsersChatsRepository;
import itmo.high_perf_sys.chat.service.UsersChatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersChatsServiceTest {

    @Mock
    private UsersChatsRepository usersChatsRepository;

    @InjectMocks
    private UsersChatsService usersChatsService;

    private UsersChats usersChats;

    @BeforeEach
    public void setUp() {
        usersChats = new UsersChats();
        usersChats.setId(UUID.randomUUID());
        usersChats.setUserId(UUID.randomUUID());
        usersChats.setChats(List.of(UUID.randomUUID(), UUID.randomUUID()));
    }

    @Test
    public void testFindByUserId() {
        UUID userId = UUID.randomUUID();
        when(usersChatsRepository.findByUserId(userId)).thenReturn(Optional.of(usersChats));

        Optional<UsersChats> result = usersChatsService.findByUserId(userId);

        assertTrue(result.isPresent());
        assertEquals(usersChats, result.get());
    }

    @Test
    public void testFindByUserIdNotFound() {
        UUID userId = UUID.randomUUID();
        when(usersChatsRepository.findByUserId(userId)).thenReturn(Optional.empty());

        Optional<UsersChats> result = usersChatsService.findByUserId(userId);

        assertFalse(result.isPresent());
    }

    @Test
    public void testSave() {
        usersChatsService.save(usersChats);
        verify(usersChatsRepository, times(1)).save(usersChats);
    }

    @Test
    public void testFindIdsByChatId() {
        UUID chatId = UUID.randomUUID();
        List<UUID> userIds = List.of(UUID.randomUUID(), UUID.randomUUID());
        when(usersChatsRepository.findIdsByChatId(chatId)).thenReturn(userIds);

        List<UUID> result = usersChatsService.findIdsByChatId(chatId);

        assertEquals(userIds, result);
    }

    @Test
    public void testFindById() {
        UUID id = UUID.randomUUID();
        when(usersChatsRepository.findById(id)).thenReturn(Optional.of(usersChats));

        Optional<UsersChats> result = usersChatsService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(usersChats, result.get());
    }

    @Test
    public void testFindByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(usersChatsRepository.findById(id)).thenReturn(Optional.empty());

        Optional<UsersChats> result = usersChatsService.findById(id);

        assertFalse(result.isPresent());
    }

    @Test
    public void testCountByChatId() {
        UUID chatId = UUID.randomUUID();
        int count = 5;
        when(usersChatsRepository.countByChatId(chatId)).thenReturn(count);

        int result = usersChatsService.countByChatId(chatId);

        assertEquals(count, result);
    }
}
