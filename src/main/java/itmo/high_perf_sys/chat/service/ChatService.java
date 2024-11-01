package itmo.high_perf_sys.chat.service;

import itmo.high_perf_sys.chat.dto.chat.request.CreateChatRequest;
import itmo.high_perf_sys.chat.dto.chat.response.*;
import itmo.high_perf_sys.chat.entity.Chat;
import itmo.high_perf_sys.chat.entity.ChatType;
import itmo.high_perf_sys.chat.entity.Message;
import itmo.high_perf_sys.chat.entity.UsersChats;
import itmo.high_perf_sys.chat.repository.chat.ChatRepository;
import itmo.high_perf_sys.chat.utils.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final UsersChatsService usersChatsService;
    private final MessageService messageService;

    private final ConcurrentHashMap<UUID, List<MessageForResponse>> chatMessages = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, ConcurrentLinkedQueue<DeferredResult<MessageForResponse>>> chatClients = new ConcurrentHashMap<>();


    @Autowired
    public ChatService(ChatRepository chatRepository, UsersChatsService usersChatsService, MessageService messageService) {
        this.chatRepository = chatRepository;
        this.usersChatsService = usersChatsService;
        this.messageService = messageService;
    }

    public UUID createChat(CreateChatRequest createChatRequest) {
        try {
            Chat chatForSave = new Chat();
            chatForSave.setId(UUID.randomUUID());
            chatForSave.setChatType(createChatRequest.getChatType());
            chatForSave.setName(createChatRequest.getName());
            Chat savedChat = chatRepository.save(chatForSave);
            List<UUID> listUsersIds = createChatRequest.getUsers();
            for (UUID listUsersId : listUsersIds) {
                UsersChats usersChats = usersChatsService.findByUserId(listUsersId).get();
                usersChats.getChats().add(savedChat.getId());
                usersChats.setId(UUID.randomUUID());
                usersChatsService.save(usersChats);
            }
            return savedChat.getId();
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.ERROR_DB_REQUEST, e);
        }
    }

    public UUID sendMessage(Message message) {
        UUID chatId = message.getChatId().getId();
        message.setId(UUID.randomUUID());
        messageService.save(message);
        MessageForResponse messageForResponse = new MessageForResponse(message);
        List<MessageForResponse> messages = chatMessages.computeIfAbsent(chatId, k -> new ArrayList<>());
        messages.add(messageForResponse);

        ConcurrentLinkedQueue<DeferredResult<MessageForResponse>> clients = chatClients.get(chatId);
        if (clients != null) {
            clients.forEach(client -> client.setResult(messageForResponse));
            clients.clear();
        }

        return message.getId();
    }

    public DeferredResult<MessageForResponse> subscribeOnChat(UUID chatId) {
        DeferredResult<MessageForResponse> result = new DeferredResult<>();
        chatClients.computeIfAbsent(chatId, k -> new ConcurrentLinkedQueue<>()).add(result);
        result.onCompletion(() -> {
            ConcurrentLinkedQueue<DeferredResult<MessageForResponse>> clients = chatClients.get(chatId);
            if (clients != null) {
                clients.remove(result);
            }
        });
        return result;
    }

    public ResponseSearchChat searchChat(UUID userId, String request, Long pageNumber, Long countChatsOnPage) {
        try {
            List<UUID> userChats = usersChatsService.findByUserId(userId).get().getChats();
            List<Chat> listOfChats = chatRepository.findByNameContainingAndIdIn(userChats, request, PageRequest.of(pageNumber.intValue(), countChatsOnPage.intValue())).stream().toList();
            ResponseSearchChat responseSearchChat = new ResponseSearchChat();
            for (int i = 0; i < listOfChats.size(); i++) {
                ChatForResponse chatForResponse = new ChatForResponse();
                Optional<Message> lastMessageOpt = messageService.findLastByChatId(listOfChats.get(i).getId());
                if (lastMessageOpt.isPresent()) {
                    Message lastMessage = lastMessageOpt.get();
                    chatForResponse.setLastMessage(lastMessage.getText());
                    chatForResponse.setLastMessageHavePhoto(lastMessage.getPhoto().length != 0);
                } else {
                    chatForResponse.setLastMessage("");
                    chatForResponse.setLastMessageHavePhoto(false);
                }
                Chat chat = chatRepository.findById(listOfChats.get(i).getId()).get();
                chatForResponse.setChatType(ChatType.values()[chat.getChatType()]);
                chatForResponse.setCountMembers(usersChatsService.countByChatId(listOfChats.get(i).getId()));
                responseSearchChat.response.set(i, chatForResponse);
            }
            return responseSearchChat;
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.ERROR_DB_REQUEST, e);
        }
    }

    public ResponseSearchMessage searchMessage(UUID chatId, String request, Long pageNumber, Long countMessagesOnPage) {
        try {
            return new ResponseSearchMessage(messageService.findByTextContainingAndChatId(chatId, request, PageRequest.of(pageNumber.intValue(), countMessagesOnPage.intValue())).stream()
                    .map(MessageForResponse::new)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.ERROR_DB_REQUEST, e);
        }
    }

    public void deleteChat(UUID chatId) {
        try {
            List<UUID> usersChatsIds = usersChatsService.findIdsByChatId(chatId);
            for (UUID usersChatsId : usersChatsIds) {
                UsersChats usersChats = usersChatsService.findById(usersChatsId).get();
                usersChats.getChats().remove(chatId);
                usersChatsService.save(usersChats);
            }
            chatRepository.deleteById(chatId);
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.ERROR_DB_REQUEST, e);
        }
    }

    public ResponseGettingChats getAllChatsByUserId(UUID userId, Long pageNumber, Long countChatsOnPage) {
        try {
            List<UUID> usersChats = usersChatsService.findByUserId(userId).get().getChats();
            int startIndex = (int) (pageNumber * countChatsOnPage);
            int toIndex = (int) (startIndex + countChatsOnPage);
            List<UUID> userChats = usersChats.subList(startIndex, toIndex);
            ResponseGettingChats responseGettingChats = new ResponseGettingChats();
            for (int i = 0; i < userChats.size(); i++) {
                ChatForResponse chatForResponse = new ChatForResponse();
                Optional<Message> lastMessageOpt = messageService.findLastByChatId(userChats.get(i));
                if (lastMessageOpt.isPresent()) {
                    Message lastMessage = lastMessageOpt.get();
                    chatForResponse.setLastMessage(lastMessage.getText());
                    chatForResponse.setLastMessageHavePhoto(lastMessage.getPhoto().length != 0);
                } else {
                    chatForResponse.setLastMessage("");
                    chatForResponse.setLastMessageHavePhoto(false);
                }
                Chat chat = chatRepository.findById(userChats.get(i)).get();
                chatForResponse.setChatType(ChatType.values()[chat.getChatType()]);
                chatForResponse.setCountMembers(usersChatsService.countByChatId(userChats.get(i)));
                responseGettingChats.response.set(i, chatForResponse);
            }
            return responseGettingChats;
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.ERROR_DB_REQUEST, e);
        }
    }

    public ResponseGettingMessages getAllMessagesByChatId(UUID chatId, Long pageNumber, Long countMessagesOnPage) {
        try {
            Page<Message> pageOfMessages = messageService.findByChatId(chatId, PageRequest.of(pageNumber.intValue(), countMessagesOnPage.intValue()));
            return new ResponseGettingMessages(
                    pageOfMessages.stream()
                            .map(MessageForResponse::new)
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.ERROR_DB_REQUEST, e);
        }
    }
}
