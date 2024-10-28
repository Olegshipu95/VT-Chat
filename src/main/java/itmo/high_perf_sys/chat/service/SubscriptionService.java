package itmo.high_perf_sys.chat.service;

import itmo.high_perf_sys.chat.entity.Subscribers;
import itmo.high_perf_sys.chat.repository.SubRepository;
import itmo.high_perf_sys.chat.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class SubscriptionService {
    private final UserRepository userRepository;
    private final SubRepository subscribersRepository;

    @Autowired
    public SubscriptionService(UserRepository userRepository, SubRepository subscribersRepository) {
        this.userRepository = userRepository;
        this.subscribersRepository = subscribersRepository;
    }

    public Subscribers addSubscriber(UUID userId, UUID subscribedUserId) {
        log.debug("start addSubscriber with userId: {} subscribedUserId: {}", userId, subscribedUserId);
        if (!userRepository.existsById(userId)) {
            log.info("userId {} does not exist", userId);
            throw new IllegalArgumentException("User with ID " + userId + " does not exist.");
        }

        if (!userRepository.existsById(subscribedUserId)) {
            log.info("Subscribed user {} does not exist", subscribedUserId);
            throw new IllegalArgumentException("Subscribed user with ID " + subscribedUserId + " does not exist.");
        }

        Subscribers subscriber = new Subscribers();
        subscriber.setUserId(userId);
        subscriber.setSubscribedUserId(subscribedUserId);
        subscriber.setSubscriptionTime(LocalDateTime.now());

        return subscribersRepository.save(subscriber);
    }
}
