package itmo.high_perf_sys.chat.service;

import itmo.high_perf_sys.chat.dto.chat.request.CreateChatRequest;
import itmo.high_perf_sys.chat.dto.chat.response.ResponseGettingChats;
import itmo.high_perf_sys.chat.dto.chat.response.ResponseGettingMessages;
import itmo.high_perf_sys.chat.dto.chat.response.ResponseSearchChat;
import itmo.high_perf_sys.chat.dto.chat.response.ResponseSearchMessage;
import itmo.high_perf_sys.chat.dto.customer.request.CreateUserAccountRequest;
import itmo.high_perf_sys.chat.dto.customer.request.UpdateUserInfoRequest;
import itmo.high_perf_sys.chat.dto.customer.response.GetUserInfoResponse;
import itmo.high_perf_sys.chat.dto.subs.request.CreateSubRequest;
import itmo.high_perf_sys.chat.dto.subs.response.SubscriptionResponse;
import itmo.high_perf_sys.chat.entity.*;
import itmo.high_perf_sys.chat.exception.UserAccountNotFoundException;
import itmo.high_perf_sys.chat.repository.SubRepository;
import itmo.high_perf_sys.chat.repository.UserRepository;
import itmo.high_perf_sys.chat.repository.chat.ChatRepository;
import itmo.high_perf_sys.chat.repository.chat.UsersChatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ChatServiceTest {

    private ChatRepository chatRepository;
    private UsersChatsService usersChatsService;
    private MessageService messageService;
    private ChatService chatService;

    @BeforeEach
    public void setUp() {
        chatRepository = mock(ChatRepository.class);
        usersChatsService = mock(UsersChatsService.class);
        messageService = mock(MessageService.class);
        chatService = new ChatService(chatRepository, usersChatsService, messageService);
    }

    @Test
    public void testFindById() {
        UUID chatId = UUID.randomUUID();
        Chat mockChat = new Chat();
        mockChat.setId(chatId);

        when(chatRepository.findById(chatId)).thenReturn(Optional.of(mockChat));

        Chat result = chatService.findById(chatId);

        assertNotNull(result);
        assertEquals(chatId, result.getId());
        verify(chatRepository, times(1)).findById(chatId);
    }

    @Test
    public void testGetAllChatsByUserId() {
        UUID userId = UUID.randomUUID();
        List<UUID> chatIds = List.of(UUID.randomUUID(), UUID.randomUUID());

        var userChats = new UsersChats();
        userChats.setUserId(userId);
        userChats.setChats(chatIds);
        when(usersChatsService.findByUserId(userId)).thenReturn(Optional.of(userChats));
        when(chatRepository.findById(any(UUID.class))).thenReturn(Optional.of(new Chat()));

        ResponseGettingChats result = chatService.getAllChatsByUserId(userId, 0L, 2L);

        assertNotNull(result);
        assertEquals(2, result.getResponse().size());
        verify(usersChatsService, times(1)).findByUserId(userId);
        verify(chatRepository, times(2)).findById(any(UUID.class));
    }

    @Test
    public void testGetAllMessagesByChatId() {
        UUID chatId = UUID.randomUUID();

        when(messageService.findByChatId(any(UUID.class), any(PageRequest.class)))
                .thenReturn(mock(Page.class));

        ResponseGettingMessages result = chatService.getAllMessagesByChatId(chatId, 0L, 10L);

        assertNotNull(result);
        verify(messageService, times(1)).findByChatId(any(UUID.class), any(PageRequest.class));
    }
}
