package itmo.high_perf_sys.chat.service;

import itmo.high_perf_sys.chat.dto.chat.request.CreateChatRequest;
import itmo.high_perf_sys.chat.dto.chat.response.*;
import itmo.high_perf_sys.chat.entity.*;
import itmo.high_perf_sys.chat.repository.chat.ChatRepository;
import itmo.high_perf_sys.chat.repository.chat.MessageRepository;
import itmo.high_perf_sys.chat.repository.chat.UsersChatsRepository;
import itmo.high_perf_sys.chat.utils.ErrorMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private ChatRepository chatRepository;
    private UsersChatsRepository usersChatsRepository;
    private MessageRepository messageRepository;

    public Long createChat(CreateChatRequest createChatRequest) {
        try {
            Chat chatForSave = new Chat();
            chatForSave.setChatType(createChatRequest.getChatType());
            chatForSave.setName(createChatRequest.getName());
            Chat savedChat = chatRepository.save(chatForSave);
            List<Long> listUsersIds = createChatRequest.getUsers();
            for (Long listUsersId : listUsersIds) {
                UsersChats usersChats = usersChatsRepository.findByUserId(listUsersId).get();
                usersChats.getChats().add(savedChat.getId());
                usersChatsRepository.save(usersChats);
            }
            return savedChat.getId();
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.ERROR_DB_REQUEST, e);
        }
    }

    public ResponseSearchChat searchChat(Long userId, String request, Long pageNumber, Long countChatsOnPage) {
        try {
            List<Long> userChats = usersChatsRepository.findByUserId(userId).get().getChats();
            List<Chat> listOfChats = chatRepository.findByNameContainingAndIdIn(userChats, request, PageRequest.of(pageNumber.intValue(), countChatsOnPage.intValue())).stream().toList();
            ResponseSearchChat responseSearchChat = new ResponseSearchChat();
            for (int i = 0; i < listOfChats.size(); i++) {
                ChatForResponse chatForResponse = new ChatForResponse();
                Optional<Message> lastMessageOpt = messageRepository.findLastByChatId(listOfChats.get(i).getId());
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
                chatForResponse.setCountMembers(usersChatsRepository.countByChatId(listOfChats.get(i).getId()));
                responseSearchChat.response.set(i, chatForResponse);
            }
            return responseSearchChat;
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.ERROR_DB_REQUEST, e);
        }
    }

    public ResponseSearchMessage searchMessage(Long chatId, String request, Long pageNumber, Long countMessagesOnPage) {
        try {
            return new ResponseSearchMessage(messageRepository.findByTextContainingAndChatId(chatId, request, PageRequest.of(pageNumber.intValue(), countMessagesOnPage.intValue())).stream()
                    .map(MessageForResponse::new)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.ERROR_DB_REQUEST, e);
        }
    }

    public void deleteChat(Long chatId) {
        try {
            List<Long> usersChatsIds = usersChatsRepository.findIdsByChatId(chatId);
            for (Long usersChatsId : usersChatsIds) {
                UsersChats usersChats = usersChatsRepository.findById(usersChatsId).get();
                usersChats.getChats().remove(chatId);
                usersChatsRepository.save(usersChats);
            }
            chatRepository.deleteById(chatId);
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.ERROR_DB_REQUEST, e);
        }
    }

    public ResponseGettingChats getAllChatsByUserId(Long userId, Long pageNumber, Long countChatsOnPage) {
        try {
            List<Long> usersChats = usersChatsRepository.findByUserId(userId).get().getChats();
            int startIndex = (int) (pageNumber * countChatsOnPage);
            int toIndex = (int) (startIndex + countChatsOnPage);
            List<Long> userChats = usersChats.subList(startIndex, toIndex);
            ResponseGettingChats responseGettingChats = new ResponseGettingChats();
            for (int i = 0; i < userChats.size(); i++) {
                ChatForResponse chatForResponse = new ChatForResponse();
                Optional<Message> lastMessageOpt = messageRepository.findLastByChatId(userChats.get(i));
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
                chatForResponse.setCountMembers(usersChatsRepository.countByChatId(userChats.get(i)));
                responseGettingChats.response.set(i, chatForResponse);
            }
            return responseGettingChats;
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.ERROR_DB_REQUEST, e);
        }
    }

    public ResponseGettingMessages getAllMessagesByChatId(Long chatId, Long pageNumber, Long countMessagesOnPage) {
        try {
            Page<Message> pageOfMessages = messageRepository.findByChatId(chatId, PageRequest.of(pageNumber.intValue(), countMessagesOnPage.intValue()));
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
