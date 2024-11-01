package itmo.high_perf_sys.chat.service;

import itmo.high_perf_sys.chat.entity.Message;
import itmo.high_perf_sys.chat.repository.chat.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void save(Message message) {
        messageRepository.save(message);
    }

    public Optional<Message> findLastByChatId(UUID uuid) {
        return messageRepository.findLastByChatId(uuid);
    }

    public Page<Message> findByTextContainingAndChatId(UUID chatId, String request, Pageable pageable) {
        return messageRepository.findByTextContainingAndChatId(chatId, request, pageable);
    }

    public Page<Message> findByChatId(UUID chatId, Pageable pageable) {
        return messageRepository.findByChatId(chatId, pageable);
    }


}
