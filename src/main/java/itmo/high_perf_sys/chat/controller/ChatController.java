package itmo.high_perf_sys.chat.controller;

import itmo.high_perf_sys.chat.dto.chat.request.CreateChatRequest;
import itmo.high_perf_sys.chat.dto.chat.response.ResponseGettingChats;
import itmo.high_perf_sys.chat.dto.chat.response.ResponseGettingMessages;
import itmo.high_perf_sys.chat.dto.chat.response.ResponseSearchChat;
import itmo.high_perf_sys.chat.dto.chat.response.ResponseSearchMessage;
import itmo.high_perf_sys.chat.dto.customer.request.CreateUserAccountRequest;
import itmo.high_perf_sys.chat.service.ChatService;
import itmo.high_perf_sys.chat.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/chats")
public class ChatController {
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService aChatService) {
        this.chatService = aChatService;
    }

    @PostMapping("/chat/start")
    public ResponseEntity<UUID> createChat(@RequestBody CreateChatRequest createChatRequest) {
        chatService.createChat(createChatRequest);
        return new ResponseEntity<>(UUID.randomUUID(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<UUID> searchChat(@RequestParam(value = "userId") Long userId, @RequestParam(value = "request") String request,
                                           @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Long pageNumber,
                                           @RequestParam(value = "countChatsOnPage", required = false, defaultValue = "20") Long countChatsOnPage) {
        ResponseSearchChat response = chatService.searchChat(userId, request, pageNumber, countChatsOnPage);
        return new ResponseEntity<>(UUID.randomUUID(), HttpStatus.OK);
    }

    @GetMapping("/{chatId}/search")
    public ResponseEntity<UUID> searchMessage(@PathVariable Long chatId, @RequestParam(value = "request") String request,
                                              @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Long pageNumber,
                                              @RequestParam(value = "countMessagesOnPage", required = false, defaultValue = "20") Long countMessagesOnPage) {
        ResponseSearchMessage response = chatService.searchMessage(chatId, request, pageNumber, countMessagesOnPage);
        return new ResponseEntity<>(UUID.randomUUID(), HttpStatus.OK);
    }

    @DeleteMapping("/chat/{chatId}")
    public ResponseEntity<UUID> deleteChat(@PathVariable Long chatId) {
        chatService.deleteChat(chatId);
        return new ResponseEntity<>(UUID.randomUUID(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UUID> getAllChatsByUserId(@PathVariable Long userId,
                                                    @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Long pageNumber,
                                                    @RequestParam(value = "countChatsOnPage", required = false, defaultValue = "20") Long countChatsOnPage) {
        ResponseGettingChats response = chatService.getAllChatsByUserId(userId, pageNumber, countChatsOnPage);
        return new ResponseEntity<>(UUID.randomUUID(), HttpStatus.OK);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<UUID> getAllMessagesByChatId(@PathVariable Long chatId,
                                                       @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Long pageNumber,
                                                       @RequestParam(value = "countMessagesOnPage", required = false, defaultValue = "20") Long countMessagesOnPage) {
        ResponseGettingMessages response = chatService.getAllMessagesByChatId(chatId, pageNumber, countMessagesOnPage);
        return new ResponseEntity<>(UUID.randomUUID(), HttpStatus.OK);
    }
}
