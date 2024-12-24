package chatcore.chat;

import chatcore.chat.dto.chat.request.CreateChatRequest;
import chatcore.chat.dto.chat.response.ResponseGettingChats;
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
import chatcore.chat.utils.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

    @Test
    public void testCreateChat_ChatType_0() {
        CreateChatRequest request = new CreateChatRequest();
        UUID duplicateUserId = UUID.randomUUID();
        request.setChatType(0);
        request.setName("Test Chat");
        request.setUsers(List.of(duplicateUserId, duplicateUserId));

        assertThrows(RuntimeException.class, () -> chatService.createChat(request));
    }

/*
    @Test
    public void testSearchChat_Success() {
        UUID userId = UUID.randomUUID();
        UUID chatId = UUID.randomUUID();
        UUID chatId2 = UUID.randomUUID();

        Chat chat1 = new Chat();
        chat1.setId(chatId);
        chat1.setName("Chat1");
        chat1.setChatType(ChatType.GROUP.ordinal());

        Chat chat2 = new Chat();
        chat2.setId(chatId2);
        chat2.setName("Chat2");
        chat2.setChatType(ChatType.PAIRED.ordinal());

        Message lastMessage = new Message();
        lastMessage.setId(UUID.randomUUID());
        lastMessage.setChatId(chat1);
        lastMessage.setText("Last message text");
        lastMessage.setPhoto(new byte[0]);

        UsersChats userChats = new UsersChats();
        userChats.setChats(List.of(chatId, chatId2));

        when(usersChatsService.findByUserId(userId)).thenReturn(Optional.of(userChats));
        when(chatRepository.findByNameContainingAndIdIn(eq(userChats.getChats()), eq("Chat"), any(PageRequest.class)))
                .thenReturn(List.of(chat1, chat2));
        when(messageService.findLastByChatId(chatId)).thenReturn(Optional.of(lastMessage));
        when(messageService.findLastByChatId(chatId2)).thenReturn(Optional.empty());
        when(usersChatsService.findIdsByChatId(chatId)).thenReturn(List.of(UUID.randomUUID(), UUID.randomUUID()));
        when(usersChatsService.findIdsByChatId(chatId2)).thenReturn(List.of(UUID.randomUUID()));
        when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat1));
        when(chatRepository.findById(chatId2)).thenReturn(Optional.of(chat2));

        ResponseSearchChat result = chatService.searchChat(userId, "Chat", 0L, 10L);

        assertNotNull(result);
        assertEquals(2, result.getResponse().size());

        ChatForResponse chatForResponse1 = result.getResponse().get(0);
        assertEquals(chatId, chatForResponse1.getId());
        assertEquals(ChatType.GROUP, chatForResponse1.getChatType());
        assertEquals(2, chatForResponse1.getCountMembers());
        assertEquals("Last message text", chatForResponse1.getLastMessage());
        assertFalse(chatForResponse1.isLastMessageHavePhoto());

        ChatForResponse chatForResponse2 = result.getResponse().get(1);
        assertEquals(chatId2, chatForResponse2.getId());
        assertEquals(ChatType.PAIRED, chatForResponse2.getChatType());
        assertEquals(1, chatForResponse2.getCountMembers());
        assertEquals("", chatForResponse2.getLastMessage());
        assertFalse(chatForResponse2.isLastMessageHavePhoto());

        verify(usersChatsService, times(1)).findByUserId(userId);
        verify(chatRepository, times(1)).findByNameContainingAndIdIn(eq(userChats.getChats()), eq("Chat"), any(PageRequest.class));
        verify(messageService, times(1)).findLastByChatId(chatId);
        verify(messageService, times(1)).findLastByChatId(chatId2);
        verify(usersChatsService, times(2)).findIdsByChatId(any(UUID.class));
    }
*/

    @Test
    public void testSearchChat_ExceptionHandling() {
        UUID userId = UUID.randomUUID();

        when(usersChatsService.findByUserId(userId)).thenThrow(new RuntimeException("DB Error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> chatService.searchChat(userId, "Chat", 0L, 10L));
        assertTrue(exception.getMessage().contains(ErrorMessages.ERROR_DB_REQUEST));
        assertTrue(exception.getCause().getMessage().contains("DB Error"));

        verify(usersChatsService, times(1)).findByUserId(userId);
    }
//    @Test
//    public void testSendMessage() {
//        // Setup
//        UUID chatId = UUID.randomUUID();
//        Chat chat = new Chat();
//        chat.setId(chatId);
//
//        Message message = new Message();
//        message.setChatId(chat);
//        message.setText("Test Message");
//        message.setPhoto(new byte[0]);
//
//        MessageForResponse expectedMessageResponse = new MessageForResponse(message);
//
//        doNothing().when(messageService).save(any(Message.class));
//
//        UUID result = chatService.sendMessage(message);
//
//        assertNotNull(result);
//        assertEquals(message.getId(), result);
//
//        Map<UUID, List<MessageForResponse>> chatMessages = chatService.getChatMessages();
//        assertTrue(chatMessages.containsKey(chatId));
//        assertEquals(1, chatMessages.get(chatId).size());
//        assertEquals(expectedMessageResponse.getText(), chatMessages.get(chatId).get(0).getText());
//
//        Map<UUID, ConcurrentLinkedQueue<DeferredResult<MessageForResponse>>> chatClients = chatService.getChatClients();
//        assertThrows(java.lang.NullPointerException.class,()->chatClients.get(chatId).isEmpty());
//    }

}
