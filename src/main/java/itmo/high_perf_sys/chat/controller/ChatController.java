package itmo.high_perf_sys.chat.controller;

import itmo.high_perf_sys.chat.dto.chat.request.CreateChatRequest;
import itmo.high_perf_sys.chat.dto.chat.response.*;
import itmo.high_perf_sys.chat.entity.Message;
import itmo.high_perf_sys.chat.service.ChatService;
import itmo.high_perf_sys.chat.utils.ErrorMessages;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@RestController
@RequestMapping("/chats")
public class ChatController {
    private final ChatService chatService;
    private final ConcurrentHashMap<UUID, ConcurrentLinkedQueue<DeferredResult<MessageForResponse>>> chatClients = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, List<MessageForResponse>> chatMessages = new ConcurrentHashMap<>();


    @Autowired
    public ChatController(ChatService aChatService) {
        this.chatService = aChatService;
    }

    @PostMapping("/chat/start")
    public ResponseEntity<?> createChat(@Valid @RequestBody CreateChatRequest createChatRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chatService.createChat(createChatRequest));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchChat(@NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
                                        @Min(value = 0, message = ErrorMessages.ID_CANNOT_BE_NEGATIVE)
                                        @RequestParam(value = "userId") UUID userId,
                                        @NotNull @RequestParam(value = "request") String request,
                                        @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Long pageNumber,
                                        @RequestParam(value = "countChatsOnPage", required = false, defaultValue = "20") Long countChatsOnPage) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(chatService.searchChat(userId, request, pageNumber, countChatsOnPage));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{chatId}/search")
    public ResponseEntity<?> searchMessage(@NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
                                           @Min(value = 0, message = ErrorMessages.ID_CANNOT_BE_NEGATIVE)
                                           @PathVariable UUID chatId,
                                           @NotNull(message = ErrorMessages.REQUEST_CANNOT_BE_NULL)
                                           @RequestParam(value = "request") String request,
                                           @NotNull(message = ErrorMessages.PAGE_CANNOT_BE_NULL)
                                           @Min(value = 0, message = ErrorMessages.PAGE_CANNOT_BE_NEGATIVE)
                                           @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Long pageNumber,
                                           @NotNull(message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NULL)
                                           @Min(value = 0, message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NEGATIVE)
                                           @RequestParam(value = "countMessagesOnPage", required = false, defaultValue = "20") Long countMessagesOnPage) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(chatService.searchMessage(chatId, request, pageNumber, countMessagesOnPage));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/chat/{chatId}")
    public ResponseEntity<?> deleteChat(@NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
                                        @Min(value = 0, message = ErrorMessages.ID_CANNOT_BE_NEGATIVE)
                                        @PathVariable UUID chatId) {
        try {
            chatService.deleteChat(chatId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllChatsByUserId(@NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
                                                 @Min(value = 0, message = ErrorMessages.ID_CANNOT_BE_NEGATIVE)
                                                 @PathVariable UUID userId,
                                                 @NotNull(message = ErrorMessages.PAGE_CANNOT_BE_NULL)
                                                 @Min(value = 0, message = ErrorMessages.PAGE_CANNOT_BE_NEGATIVE)
                                                 @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Long pageNumber,
                                                 @NotNull(message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NULL)
                                                 @Min(value = 0, message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NEGATIVE)
                                                 @RequestParam(value = "countChatsOnPage", required = false, defaultValue = "20") Long countChatsOnPage) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(chatService.getAllChatsByUserId(userId, pageNumber, countChatsOnPage));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<?> getAllMessagesByChatId(@NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
                                                    @Min(value = 0, message = ErrorMessages.ID_CANNOT_BE_NEGATIVE)
                                                    @PathVariable UUID chatId,
                                                    @NotNull(message = ErrorMessages.PAGE_CANNOT_BE_NULL)
                                                    @Min(value = 0, message = ErrorMessages.PAGE_CANNOT_BE_NEGATIVE)
                                                    @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Long pageNumber,
                                                    @NotNull(message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NULL)
                                                    @Min(value = 0, message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NEGATIVE)
                                                    @RequestParam(value = "countMessagesOnPage", required = false, defaultValue = "20") Long countMessagesOnPage) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(chatService.getAllMessagesByChatId(chatId, pageNumber, countMessagesOnPage));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@Valid @RequestBody Message message) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(chatService.sendMessage(message));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/subscribe/{chatId}")
    public DeferredResult<MessageForResponse> subscribe(@Valid @PathVariable UUID chatId) {
        return chatService.subscribeOnChat(chatId);
    }
}
