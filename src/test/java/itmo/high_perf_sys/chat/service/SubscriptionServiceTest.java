package itmo.high_perf_sys.chat.service;

import itmo.high_perf_sys.chat.dto.customer.request.CreateUserAccountRequest;
import itmo.high_perf_sys.chat.dto.customer.request.UpdateUserInfoRequest;
import itmo.high_perf_sys.chat.dto.customer.response.GetUserInfoResponse;
import itmo.high_perf_sys.chat.dto.subs.request.CreateSubRequest;
import itmo.high_perf_sys.chat.dto.subs.response.SubscriptionResponse;
import itmo.high_perf_sys.chat.entity.*;
import itmo.high_perf_sys.chat.exception.UserAccountNotFoundException;
import itmo.high_perf_sys.chat.repository.SubRepository;
import itmo.high_perf_sys.chat.repository.UserRepository;
import itmo.high_perf_sys.chat.repository.chat.UsersChatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

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