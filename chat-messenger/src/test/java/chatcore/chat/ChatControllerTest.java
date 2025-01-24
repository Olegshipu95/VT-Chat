package chatcore.chat;


import chatcore.chat.controller.ChatController;
import chatcore.chat.dto.chat.request.CreateChatRequest;
import chatcore.chat.dto.chat.request.SearchChatRequest;
import chatcore.chat.dto.chat.request.SearchMessageRequest;
import chatcore.chat.dto.chat.response.*;
import chatcore.chat.entity.Message;
import chatcore.chat.entity.UsersChats;
import chatcore.chat.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
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
        SearchChatRequest searchChatRequest = new SearchChatRequest();
        when(chatService.searchChat(userId, request, pageNumber, countChatsOnPage)).thenReturn(expectedResponse);
        searchChatRequest.setUserId(userId);
        searchChatRequest.setRequest(request);
        searchChatRequest.setPageNumber(pageNumber);
        searchChatRequest.setCountChatsOnPage(countChatsOnPage);

        ResponseEntity<?> response = chatController.searchChat(searchChatRequest);

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
        SearchChatRequest searchChatRequest = new SearchChatRequest();
        searchChatRequest.setUserId(userId);
        searchChatRequest.setRequest(request);
        searchChatRequest.setPageNumber(pageNumber);
        searchChatRequest.setCountChatsOnPage(countChatsOnPage);

        ResponseEntity<?> response = chatController.searchChat(searchChatRequest);

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


    @Test
    public void testSearchMessage() {
        UUID chatId = UUID.randomUUID();
        String request = "test";
        Long pageNumber = 0L;
        Long countMessagesOnPage = 20L;
        ResponseSearchMessage expectedResponse = new ResponseSearchMessage(new ArrayList<>());
        SearchMessageRequest searchMessageRequest = new SearchMessageRequest();
        searchMessageRequest.setChatId(chatId);
        searchMessageRequest.setRequest(request);
        searchMessageRequest.setPageNumber(pageNumber);
        searchMessageRequest.setCountMessagesOnPage(countMessagesOnPage);

        when(chatService.searchMessage(chatId, request, pageNumber, countMessagesOnPage)).thenReturn(expectedResponse);

        ResponseEntity<?> response = chatController.searchMessage(searchMessageRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testSearchMessageException() {
        UUID chatId = UUID.randomUUID();
        String request = "test";
        Long pageNumber = 0L;
        Long countMessagesOnPage = 20L;
        SearchMessageRequest searchMessageRequest = new SearchMessageRequest();
        searchMessageRequest.setChatId(chatId);
        searchMessageRequest.setRequest(request);
        searchMessageRequest.setPageNumber(pageNumber);
        searchMessageRequest.setCountMessagesOnPage(countMessagesOnPage);

        when(chatService.searchMessage(chatId, request, pageNumber, countMessagesOnPage)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> response = chatController.searchMessage(searchMessageRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error", response.getBody());
    }

    @Test
    public void testDeleteChat() {
        UUID chatId = UUID.randomUUID();

        doNothing().when(chatService).deleteChat(chatId);

        ResponseEntity<?> response = chatController.deleteChat(chatId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(chatService, times(1)).deleteChat(chatId);
    }

    @Test
    public void testDeleteChatException() {
        UUID chatId = UUID.randomUUID();

        doThrow(new RuntimeException("Error")).when(chatService).deleteChat(chatId);

        ResponseEntity<?> response = chatController.deleteChat(chatId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error", response.getBody());
    }

    @Test
    public void testGetAllChatsByUserId() {
        UUID userId = UUID.randomUUID();
        Long pageNumber = 0L;
        Long countChatsOnPage = 20L;
        ResponseGettingChats expectedResponse = new ResponseGettingChats();

        when(chatService.getAllChatsByUserId(userId, pageNumber, countChatsOnPage)).thenReturn(expectedResponse);

        ResponseEntity<?> response = chatController.getAllChatsByUserId(userId, pageNumber, countChatsOnPage);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testGetAllChatsByUserIdException() {
        UUID userId = UUID.randomUUID();
        Long pageNumber = 0L;
        Long countChatsOnPage = 20L;

        when(chatService.getAllChatsByUserId(userId, pageNumber, countChatsOnPage)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> response = chatController.getAllChatsByUserId(userId, pageNumber, countChatsOnPage);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error", response.getBody());
    }

    @Test
    public void testGetAllMessagesByChatId() {
        UUID chatId = UUID.randomUUID();
        Long pageNumber = 0L;
        Long countMessagesOnPage = 20L;
        ResponseGettingMessages expectedResponse = new ResponseGettingMessages(new ArrayList<>());

        when(chatService.getAllMessagesByChatId(chatId, pageNumber, countMessagesOnPage)).thenReturn(expectedResponse);

        ResponseEntity<?> response = chatController.getAllMessagesByChatId(chatId, pageNumber, countMessagesOnPage);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testGetAllMessagesByChatIdException() {
        UUID chatId = UUID.randomUUID();
        Long pageNumber = 0L;
        Long countMessagesOnPage = 20L;

        when(chatService.getAllMessagesByChatId(chatId, pageNumber, countMessagesOnPage)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> response = chatController.getAllMessagesByChatId(chatId, pageNumber, countMessagesOnPage);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error", response.getBody());
    }

    @Test
    public void testAddUserChats() {
        UsersChats usersChats = new UsersChats();
        UsersChats expectedResponse = new UsersChats();

        when(chatService.addUserChats(usersChats)).thenReturn(expectedResponse);

        ResponseEntity<?> response = chatController.addUserChats(usersChats);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testAddUserChatsException() {
        UsersChats usersChats = new UsersChats();

        when(chatService.addUserChats(usersChats)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> response = chatController.addUserChats(usersChats);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error", response.getBody());
    }
}
