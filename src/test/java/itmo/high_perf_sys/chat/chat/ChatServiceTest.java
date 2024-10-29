package itmo.high_perf_sys.chat.chat;

import itmo.high_perf_sys.chat.dto.chat.request.CreateChatRequest;
import itmo.high_perf_sys.chat.dto.chat.response.MessageForResponse;
import itmo.high_perf_sys.chat.dto.chat.response.ResponseSearchChat;
import itmo.high_perf_sys.chat.entity.Chat;
import itmo.high_perf_sys.chat.entity.ChatType;
import itmo.high_perf_sys.chat.entity.Message;
import itmo.high_perf_sys.chat.entity.UsersChats;
import itmo.high_perf_sys.chat.repository.chat.ChatRepository;
import itmo.high_perf_sys.chat.repository.chat.MessageRepository;
import itmo.high_perf_sys.chat.repository.chat.UsersChatsRepository;
import itmo.high_perf_sys.chat.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ChatServiceTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private UsersChatsRepository usersChatsRepository;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private ChatService chatService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateChat() {
        CreateChatRequest request = new CreateChatRequest();
        request.setChatType(ChatType.GROUP.ordinal());
        request.setName("Test Chat");
        List<UUID> users = new ArrayList<>();
        users.add(UUID.randomUUID());
        users.add(UUID.randomUUID());
        request.setUsers(users);

        Chat chat = new Chat();
        chat.setId(UUID.randomUUID());
        chat.setChatType(ChatType.GROUP.ordinal());
        chat.setName("Test Chat");

        UsersChats usersChats = new UsersChats();
        usersChats.setUserId(users.get(0));
        usersChats.setChats(new ArrayList<>());

        when(chatRepository.save(any(Chat.class))).thenReturn(chat);
        when(usersChatsRepository.findByUserId(users.get(0))).thenReturn(Optional.of(usersChats));
        when(usersChatsRepository.findByUserId(users.get(1))).thenReturn(Optional.of(usersChats));

        UUID result = chatService.createChat(request);

        assertEquals(chat.getId(), result);
        verify(chatRepository, times(1)).save(any(Chat.class));
        verify(usersChatsRepository, times(2)).save(any(UsersChats.class));
    }

    @Test
    public void testCreateChatException() {
        CreateChatRequest request = new CreateChatRequest();
        request.setChatType(ChatType.GROUP.ordinal());
        request.setName("Test Chat");
        List<UUID> users = new ArrayList<>();
        users.add(UUID.randomUUID());
        users.add(UUID.randomUUID());
        request.setUsers(users);

        when(usersChatsRepository.findByUserId(users.get(0))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> chatService.createChat(request));
    }

    @Test
    public void testSendMessage() {
        Message message = new Message();
        message.setId(UUID.randomUUID());
        message.setChatId(new Chat());
        message.setText("Test Message");

        when(messageRepository.save(any(Message.class))).thenReturn(message);

        UUID result = chatService.sendMessage(message);

        assertEquals(message.getId(), result);
        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    public void testSearchChat() {
        UUID userId = UUID.randomUUID();
        String request = "test";
        Long pageNumber = 0L;
        Long countChatsOnPage = 20L;

        UsersChats usersChats = new UsersChats();
        usersChats.setUserId(userId);
        List<UUID> chats = new ArrayList<>();
        chats.add(UUID.randomUUID());
        usersChats.setChats(chats);

        Chat chat = new Chat();
        chat.setId(chats.get(0));
        chat.setName("Test Chat");
        chat.setChatType(ChatType.GROUP.ordinal());

        when(usersChatsRepository.findByUserId(userId)).thenReturn(Optional.of(usersChats));
        when(chatRepository.findByNameContainingAndIdIn(anyList(), anyString(), any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(chat)));
        when(messageRepository.findLastByChatId(any(UUID.class))).thenReturn(Optional.empty());

        ResponseSearchChat result = chatService.searchChat(userId, request, pageNumber, countChatsOnPage);

        assertEquals(1, result.getResponse().size());
        verify(chatRepository, times(1)).findByNameContainingAndIdIn(anyList(), anyString(), any(PageRequest.class));
    }

    @Test
    public void testDeleteChat() {
        UUID chatId = UUID.randomUUID();

        UsersChats usersChats = new UsersChats();
        usersChats.setUserId(UUID.randomUUID());
        List<UUID> chats = new ArrayList<>();
        chats.add(chatId);
        usersChats.setChats(chats);

        when(usersChatsRepository.findIdsByChatId(chatId)).thenReturn(List.of(usersChats.getUserId()));
        when(usersChatsRepository.findById(usersChats.getUserId())).thenReturn(Optional.of(usersChats));

        chatService.deleteChat(chatId);

        verify(usersChatsRepository, times(1)).save(any(UsersChats.class));
        verify(chatRepository, times(1)).deleteById(chatId);
    }
}
