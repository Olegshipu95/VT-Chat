package chatcore.subscription.service;


import chatcore.subscription.dto.subs.request.CreateSubRequest;
import chatcore.subscription.dto.subs.response.SubscriptionResponse;
import chatcore.subscription.entity.Subscribers;
import chatcore.subscription.exception.UserAccountNotFoundException;
import chatcore.subscription.repository.SubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubscriptionService {
    private final UserService userService;
    private final SubRepository subscribersRepository;

    // Метод создания подписки
    @Transactional
    public UUID createSub(CreateSubRequest sub) {
        log.debug("CREATE: start addSubscriber with userId: {} subscribedUserId: {}", sub.userId(), sub.subscribedUserId());
        validateUsersExist(sub.userId(), sub.subscribedUserId());
        UUID newId = UUID.randomUUID();
        Subscribers subscriber = new Subscribers();
        subscriber.setId(newId);
        subscriber.setUserId(sub.userId());
        subscriber.setSubscribedUserId(sub.subscribedUserId());
        subscriber.setSubscriptionTime(LocalDateTime.now());
        var newSub = subscribersRepository.save(subscriber);
        log.info("CREATE: new sub {}", newSub.getId());
        return newSub.getId();
    }

    @Transactional(readOnly = true)
    public Subscribers getSub(UUID subId) {
        log.debug("GET: subscriber with ID: {}", subId);
        return subscribersRepository.findById(subId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription with ID " + subId + " not found."));
    }

    @Transactional
    public void deleteSub(UUID subId) {
        log.debug("DELETE: subscriber with ID: {}", subId);
        if (!subscribersRepository.existsById(subId)) {
            log.info("Subscription with ID {} does not exist", subId);
            throw new IllegalArgumentException("Subscription with ID " + subId + " does not exist.");
        }
        subscribersRepository.deleteById(subId);
    }

    @Transactional(readOnly = true)
    public List<SubscriptionResponse> getSubscriptionsByUserId(UUID userId) {
        log.info("GET: subscriptions for userId: {}", userId);
        return subscribersRepository.getSubResponseByUserId(userId);
    }

    private void validateUsersExist(UUID userId, UUID subscribedUserId) {
        if (!userService.existsById(userId)) {
            log.info("User ID {} does not exist", userId);
            throw new UserAccountNotFoundException(userId);
        }
        if (!userService.existsById(subscribedUserId)) {
            log.info("Subscribed User ID {} does not exist", subscribedUserId);
            throw new UserAccountNotFoundException(subscribedUserId);
        }
    }
}