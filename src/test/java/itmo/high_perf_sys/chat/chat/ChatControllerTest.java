package itmo.high_perf_sys.chat.chat;

import itmo.high_perf_sys.chat.controller.ChatController;
import itmo.high_perf_sys.chat.dto.chat.request.CreateChatRequest;
import itmo.high_perf_sys.chat.dto.chat.response.MessageForResponse;
import itmo.high_perf_sys.chat.dto.chat.response.ResponseSearchChat;
import itmo.high_perf_sys.chat.entity.Message;
import itmo.high_perf_sys.chat.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ChatControllerTest {

    @Mock
    private ChatService chatService;

    @InjectMocks
    private ChatController chatController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateChat() {
        CreateChatRequest request = new CreateChatRequest();
        UUID chatId = UUID.randomUUID();
        when(chatService.createChat(request)).thenReturn(chatId);

        ResponseEntity<?> response = chatController.createChat(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(chatId, response.getBody());
    }

    @Test
    public void testCreateChatException() {
        CreateChatRequest request = new CreateChatRequest();
        when(chatService.createChat(request)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> response = chatController.createChat(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error", response.getBody());
    }

    @Test
    public void testSearchChat() {
        UUID userId = UUID.randomUUID();
        String request = "test";
        Long pageNumber = 0L;
        Long countChatsOnPage = 20L;
        ResponseSearchChat expectedResponse = new ResponseSearchChat();
        when(chatService.searchChat(userId, request, pageNumber, countChatsOnPage)).thenReturn(expectedResponse);

        ResponseEntity<?> response = chatController.searchChat(userId, request, pageNumber, countChatsOnPage);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testSearchChatException() {
        UUID userId = UUID.randomUUID();
        String request = "test";
        Long pageNumber = 0L;
        Long countChatsOnPage = 20L;
        when(chatService.searchChat(userId, request, pageNumber, countChatsOnPage)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> response = chatController.searchChat(userId, request, pageNumber, countChatsOnPage);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error", response.getBody());
    }

    @Test
    public void testSendMessage() {
        Message message = new Message();
        UUID response = UUID.randomUUID();
        when(chatService.sendMessage(message)).thenReturn(response);

        ResponseEntity<?> result = chatController.sendMessage(message);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    public void testSendMessageException() {
        Message message = new Message();
        when(chatService.sendMessage(message)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = chatController.sendMessage(message);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    public void testSubscribe() {
        UUID chatId = UUID.randomUUID();
        DeferredResult<MessageForResponse> deferredResult = new DeferredResult<>();
        when(chatService.subscribeOnChat(chatId)).thenReturn(deferredResult);

        DeferredResult<MessageForResponse> result = chatController.subscribe(chatId, null);

        assertEquals(deferredResult, result);
    }
}
