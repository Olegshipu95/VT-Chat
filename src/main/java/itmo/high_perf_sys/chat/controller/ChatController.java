package itmo.high_perf_sys.chat.controller;

import itmo.high_perf_sys.chat.dto.chat.request.CreateChatRequest;
import itmo.high_perf_sys.chat.dto.chat.request.SearchChatRequest;
import itmo.high_perf_sys.chat.dto.chat.request.SearchMessageRequest;
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
    public ResponseEntity<?> createChat(@Valid @RequestBody CreateChatRequest createChatRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chatService.createChat(createChatRequest));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchChat(@Valid @RequestBody SearchChatRequest searchChatRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(chatService.searchChat(searchChatRequest.getUserId(), searchChatRequest.getRequest(), searchChatRequest.getPageNumber(), searchChatRequest.getCountChatsOnPage()));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{chatId}/search")
    public ResponseEntity<?> searchMessage(@Valid @RequestBody SearchMessageRequest searchMessageRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(chatService.searchMessage(searchMessageRequest.getChatId(), searchMessageRequest.getRequest(), searchMessageRequest.getPageNumber(), searchMessageRequest.getCountMessagesOnPage()));
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
