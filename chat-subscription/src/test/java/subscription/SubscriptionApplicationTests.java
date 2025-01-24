package subscription;

import subscription.dto.subs.request.CreateSubRequest;
import subscription.dto.subs.response.SubscriptionResponse;
import subscription.entity.Subscribers;
import subscription.exception.UserAccountNotFoundException;
import subscription.repository.SubRepository;
import subscription.service.SubscriptionService;
import subscription.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    private UserService userService;
    private SubRepository subRepository;
    private SubscriptionService subscriptionService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        subRepository = mock(SubRepository.class);
        subscriptionService = new SubscriptionService(userService, subRepository);
    }

    @Test
    public void testCreateSub() {
        UUID userId = UUID.randomUUID();
        UUID subscribedUserId = UUID.randomUUID();
        CreateSubRequest request = mock(CreateSubRequest.class);

        when(request.userId()).thenReturn(userId);
        when(request.subscribedUserId()).thenReturn(subscribedUserId);

        when(userService.existsById(userId)).thenReturn(true);
        when(userService.existsById(subscribedUserId)).thenReturn(true);

        UUID subId = UUID.randomUUID();
        Subscribers newSubscriber = new Subscribers();
        newSubscriber.setId(subId);

        when(subRepository.save(any(Subscribers.class))).thenReturn(newSubscriber);

        UUID result = subscriptionService.createSub(request);

        assertNotNull(result);
        assertEquals(subId, result);
        verify(userService, times(1)).existsById(userId);
        verify(userService, times(1)).existsById(subscribedUserId);
        verify(subRepository, times(1)).save(any(Subscribers.class));
    }

    @Test
    public void testCreateSubThrowsWhenUserNotFound() {
        UUID userId = UUID.randomUUID();
        UUID subscribedUserId = UUID.randomUUID();
        CreateSubRequest request = mock(CreateSubRequest.class);

        when(request.userId()).thenReturn(userId);
        when(request.subscribedUserId()).thenReturn(subscribedUserId);

        when(userService.existsById(userId)).thenReturn(false);

        assertThrows(UserAccountNotFoundException.class, () -> subscriptionService.createSub(request));
        verify(userService, times(1)).existsById(userId);
        verify(userService, never()).existsById(subscribedUserId);
        verify(subRepository, never()).save(any(Subscribers.class));
    }

    @Test
    void testGetSub() {
        UUID subId = UUID.randomUUID();
        Subscribers subscriber = new Subscribers();
        subscriber.setId(subId);

        when(subRepository.findById(subId)).thenReturn(Optional.of(subscriber));

        Subscribers result = subscriptionService.getSub(subId);

        assertNotNull(result);
        assertEquals(subId, result.getId());
        verify(subRepository, times(1)).findById(subId);
    }

    @Test
    void testGetSubThrowsWhenNotFound() {
        UUID subId = UUID.randomUUID();

        when(subRepository.findById(subId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> subscriptionService.getSub(subId));
        verify(subRepository, times(1)).findById(subId);
    }

    @Test
    void testDeleteSub() {
        UUID subId = UUID.randomUUID();

        when(subRepository.existsById(subId)).thenReturn(true);

        subscriptionService.deleteSub(subId);

        verify(subRepository, times(1)).existsById(subId);
        verify(subRepository, times(1)).deleteById(subId);
    }

    @Test
    void testDeleteSubThrowsWhenNotFound() {
        UUID subId = UUID.randomUUID();

        when(subRepository.existsById(subId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> subscriptionService.deleteSub(subId));
        verify(subRepository, times(1)).existsById(subId);
        verify(subRepository, never()).deleteById(subId);
    }

    @Test
    void testGetSubscriptionsByUserId() {
        UUID userId = UUID.randomUUID();

        SubscriptionResponse response1 = new SubscriptionResponse(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now());
        SubscriptionResponse response2 = new SubscriptionResponse(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now());
        List<SubscriptionResponse> mockResponseList = List.of(response1, response2);

        when(subRepository.getSubResponseByUserId(userId)).thenReturn(mockResponseList);

        List<SubscriptionResponse> result = subscriptionService.getSubscriptionsByUserId(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(mockResponseList, result);
        verify(subRepository, times(1)).getSubResponseByUserId(userId);
    }
}