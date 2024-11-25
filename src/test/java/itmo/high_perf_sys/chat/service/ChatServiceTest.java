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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    private Chat chat;
    private UsersChats usersChats;
    private Message message;

    @BeforeEach
    public void setUp() {
        chatRepository = mock(ChatRepository.class);
        usersChatsService = mock(UsersChatsService.class);
        messageService = mock(MessageService.class);
        chatService = new ChatService(chatRepository, usersChatsService, messageService);

        chat = new Chat();
        chat.setId(UUID.randomUUID());
        chat.setChatType(ChatType.GROUP.ordinal());
        chat.setName("Test Chat");

        usersChats = new UsersChats();
        usersChats.setId(UUID.randomUUID());
        usersChats.setChats(new ArrayList<>());

        message = new Message();
        message.setId(UUID.randomUUID());
        message.setChatId(chat);
        message.setText("Test Message");
        message.setPhoto(new byte[0]);
    }

    @Test
    public void testFindById_NotFound() {
        UUID chatId = UUID.randomUUID();
        when(chatRepository.findById(chatId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> chatService.findById(chatId));
        verify(chatRepository, times(1)).findById(chatId);
    }


    @Test
    public void testCreateChat_DuplicateUsers() {
        CreateChatRequest request = new CreateChatRequest();
        UUID duplicateUserId = UUID.randomUUID();
        request.setChatType(ChatType.PAIRED.ordinal());
        request.setName("Test Chat");
        request.setUsers(List.of(duplicateUserId, duplicateUserId));

        assertThrows(RuntimeException.class, () -> chatService.createChat(request));
    }


    @Test
    public void testSubscribeOnChat() {
        UUID chatId = UUID.randomUUID();

        var result = chatService.subscribeOnChat(chatId);

        assertNotNull(result);
        assertFalse(result.isSetOrExpired());
    }


    @Test
    public void testSearchMessage() {
        UUID chatId = UUID.randomUUID();

        when(messageService.findByTextContainingAndChatId(eq(chatId), anyString(), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(message)));

        ResponseSearchMessage result = chatService.searchMessage(chatId, "Test", 0L, 10L);

        assertNotNull(result);
        assertEquals(1, result.getListOfMessages().size());
        verify(messageService, times(1))
                .findByTextContainingAndChatId(eq(chatId), anyString(), any(PageRequest.class));
    }

    @Test
    public void testDeleteChat_NotFound() {
        UUID chatId = UUID.randomUUID();

        when(usersChatsService.findIdsByChatId(chatId)).thenReturn(Collections.emptyList());

        chatService.deleteChat(chatId);

        verify(chatRepository, times(1)).deleteById(chatId);
        verify(usersChatsService, times(1)).findIdsByChatId(chatId);
    }

    @Test
    public void testDeleteChat() {
        UUID chatId = UUID.randomUUID();
        UUID userChatId = UUID.randomUUID();
        List<UUID> userChatIds = List.of(userChatId);

        when(usersChatsService.findIdsByChatId(chatId)).thenReturn(userChatIds);
        when(usersChatsService.findById(userChatId)).thenReturn(Optional.of(usersChats));

        chatService.deleteChat(chatId);

        verify(usersChatsService, times(1)).findIdsByChatId(chatId);
        verify(usersChatsService, times(1)).findById(userChatId);
        verify(usersChatsService, times(1)).save(any(UsersChats.class));
        verify(chatRepository, times(1)).deleteById(chatId);
    }

    @Test
    public void testGetAllChatsByUserId_NoChats() {
        UUID userId = UUID.randomUUID();

        when(usersChatsService.findByUserId(userId)).thenReturn(Optional.of(usersChats));

        ResponseGettingChats result = chatService.getAllChatsByUserId(userId, 0L, 10L);

        assertNotNull(result);
        assertEquals(0, result.getResponse().size());
        verify(usersChatsService, times(1)).findByUserId(userId);
    }

    @Test
    public void testSearchMessage_EmptyText() {
        UUID chatId = UUID.randomUUID();

        when(messageService.findByTextContainingAndChatId(eq(chatId), eq(""), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        ResponseSearchMessage result = chatService.searchMessage(chatId, "", 0L, 10L);

        assertNotNull(result);
        assertTrue(result.getListOfMessages().isEmpty());
        verify(messageService, times(1)).findByTextContainingAndChatId(eq(chatId), eq(""), any(PageRequest.class));
    }

    @Test
    public void testDeleteChat_UserWithoutChats() {
        UUID chatId = UUID.randomUUID();
        UUID userChatId = UUID.randomUUID();

        when(usersChatsService.findIdsByChatId(chatId)).thenReturn(List.of(userChatId));
        when(usersChatsService.findById(userChatId)).thenReturn(Optional.of(usersChats));
        usersChats.setChats(Collections.emptyList()); // У пользователя нет чатов

        chatService.deleteChat(chatId);

        verify(usersChatsService, times(1)).findIdsByChatId(chatId);
        verify(usersChatsService, times(1)).findById(userChatId);
        verify(usersChatsService, times(1)).save(usersChats);
        verify(chatRepository, times(1)).deleteById(chatId);
    }

    @Test
    public void testDeleteChat_ChatNotFound() {
        UUID chatId = UUID.randomUUID();

        when(usersChatsService.findIdsByChatId(chatId)).thenReturn(Collections.emptyList());

        chatService.deleteChat(chatId);

        verify(chatRepository, times(1)).deleteById(chatId);
        verify(usersChatsService, times(1)).findIdsByChatId(chatId);
    }

    @Test
    public void testGetAllMessagesByChatId_MultipleMessages() {
        UUID chatId = UUID.randomUUID();
        Message message1 = new Message();
        message1.setId(UUID.randomUUID());
        message1.setText("Message 1");
        Message message2 = new Message();
        message2.setId(UUID.randomUUID());
        message2.setText("Message 2");

        when(messageService.findByChatId(eq(chatId), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(message1, message2)));

        ResponseGettingMessages result = chatService.getAllMessagesByChatId(chatId, 0L, 10L);

        assertNotNull(result);
        assertEquals(2, result.getResponse().size());
        verify(messageService, times(1)).findByChatId(eq(chatId), any(PageRequest.class));
    }

    @Test
    public void testGetAllChatsByUserId_WithChats() {
        UUID userId = UUID.randomUUID();
        Chat chat1 = new Chat();
        chat1.setId(UUID.randomUUID());
        Chat chat2 = new Chat();
        chat2.setId(UUID.randomUUID());

        usersChats.setChats(List.of(UUID.randomUUID(), UUID.randomUUID()));

        when(usersChatsService.findByUserId(userId)).thenReturn(Optional.of(usersChats));

        assertThrows(java.lang.RuntimeException.class, () -> chatService.getAllChatsByUserId(userId, 0L, 10L));
    }
}
