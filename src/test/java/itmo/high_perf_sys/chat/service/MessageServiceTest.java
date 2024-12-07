package itmo.high_perf_sys.chat.service;

import itmo.high_perf_sys.chat.entity.Chat;
import itmo.high_perf_sys.chat.entity.Message;
import itmo.high_perf_sys.chat.repository.chat.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageService messageService;

    private Message message;

    @BeforeEach
    public void setUp() {
        message = new Message();
        message.setId(UUID.randomUUID());
        message.setChatId(new Chat());
        message.setText("Test Message");
        message.setPhoto(new byte[0]);
    }

    @Test
    public void testSave() {
        messageService.save(message);
        verify(messageRepository, times(1)).save(message);
    }

    @Test
    public void testFindLastByChatId() {
        UUID chatId = UUID.randomUUID();
        when(messageRepository.findLastByChatId(chatId)).thenReturn(Optional.of(message));

        Optional<Message> result = messageService.findLastByChatId(chatId);

        assertTrue(result.isPresent());
        assertEquals(message, result.get());
    }

    @Test
    public void testFindLastByChatIdNotFound() {
        UUID chatId = UUID.randomUUID();
        when(messageRepository.findLastByChatId(chatId)).thenReturn(Optional.empty());

        Optional<Message> result = messageService.findLastByChatId(chatId);

        assertFalse(result.isPresent());
    }

    @Test
    public void testFindByTextContainingAndChatId() {
        UUID chatId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Message> page = new PageImpl<>(List.of(message));
        when(messageRepository.findByTextContainingAndChatId(chatId, "Test", pageable)).thenReturn(page);

        Page<Message> result = messageService.findByTextContainingAndChatId(chatId, "Test", pageable);

        assertEquals(page, result);
    }

    @Test
    public void testFindByChatId() {
        UUID chatId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Message> page = new PageImpl<>(List.of(message));
        when(messageRepository.findByChatId(chatId, pageable)).thenReturn(page);

        Page<Message> result = messageService.findByChatId(chatId, pageable);

        assertEquals(page, result);
    }
}
