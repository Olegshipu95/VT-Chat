package chatcore.chat.chat;

import chatcore.chat.dto.chat.request.CreateChatRequest;
import chatcore.chat.dto.chat.response.ResponseGettingMessages;
import chatcore.chat.dto.chat.response.ResponseSearchMessage;
import chatcore.chat.entity.Chat;
import chatcore.chat.entity.ChatType;
import chatcore.chat.entity.Message;
import chatcore.chat.entity.UsersChats;
import chatcore.chat.repository.chat.ChatRepository;
import chatcore.chat.service.ChatService;
import chatcore.chat.service.MessageService;
import chatcore.chat.service.UsersChatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private UsersChatsService usersChatsService;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private ChatService chatService;

    private Chat chat;
    private UsersChats usersChats;
    private Message message;

    @BeforeEach
    public void setUp() {
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
    public void testFindById() {
        when(chatRepository.findById(any(UUID.class))).thenReturn(Optional.of(chat));

        Chat result = chatService.findById(chat.getId());

        assertEquals(chat, result);
    }

    @Test
    public void testCreateChat() {
        CreateChatRequest request = new CreateChatRequest();
        request.setChatType(ChatType.PAIRED.ordinal());
        request.setName("Test Chat");
        request.setUsers(List.of(UUID.randomUUID(), UUID.randomUUID()));

        when(chatRepository.save(any(Chat.class))).thenReturn(chat);
        when(usersChatsService.findByUserId(any(UUID.class))).thenReturn(Optional.of(usersChats));

        UUID result = chatService.createChat(request);

        assertEquals(chat.getId(), result);
        verify(chatRepository, times(1)).save(any(Chat.class));
        verify(usersChatsService, times(2)).findByUserId(any(UUID.class));
        verify(usersChatsService, times(2)).save(any(UsersChats.class));
    }

    @Test
    public void testSearchMessage() {
        when(messageService.findByTextContainingAndChatId(any(UUID.class), anyString(), any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(message)));

        ResponseSearchMessage result = chatService.searchMessage(chat.getId(), "Test", 0L, 10L);

        assertNotNull(result);
        assertEquals(1, result.getListOfMessages().size());
    }

    @Test
    public void testDeleteChat() {
        when(usersChatsService.findIdsByChatId(any(UUID.class))).thenReturn(List.of(usersChats.getId()));
        when(usersChatsService.findById(any(UUID.class))).thenReturn(Optional.of(usersChats));

        chatService.deleteChat(chat.getId());

        verify(usersChatsService, times(1)).findIdsByChatId(any(UUID.class));
        verify(usersChatsService, times(1)).findById(any(UUID.class));
        verify(usersChatsService, times(1)).save(any(UsersChats.class));
        verify(chatRepository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    public void testGetAllMessagesByChatId() {
        when(messageService.findByChatId(any(UUID.class), any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(message)));

        ResponseGettingMessages result = chatService.getAllMessagesByChatId(chat.getId(), 0L, 10L);

        assertNotNull(result);
        assertEquals(1, result.getResponse().size());
    }

    @Test
    public void testDeleteChat_Exception() {
        when(usersChatsService.findIdsByChatId(any(UUID.class))).thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> chatService.deleteChat(chat.getId()));

        verify(chatRepository, never()).deleteById(any(UUID.class));
    }

}
