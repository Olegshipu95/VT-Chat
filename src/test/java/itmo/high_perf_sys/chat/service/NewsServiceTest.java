package itmo.high_perf_sys.chat.service;

import itmo.high_perf_sys.chat.dto.feed.response.FeedResponse;
import itmo.high_perf_sys.chat.entity.*;
import itmo.high_perf_sys.chat.repository.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private NewsService newsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPostsBySubscriber_EmptyResponse() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Long page = 0L;
        Long count = 10L;

        Page<Post> emptyPage = new PageImpl<>(List.of());

        when(newsRepository.findPostsBySubscriber(eq(userId), any(PageRequest.class))).thenReturn(emptyPage);

        // Act
        FeedResponse response = newsService.getPostsBySubscriber(userId, page, count);

        // Assert
        assertNotNull(response);
        assertTrue(response.feed().isEmpty());

        verify(newsRepository, times(1)).findPostsBySubscriber(eq(userId), any(PageRequest.class));
    }

    @Test
    public void testGetPostsBySubscriber_Exception() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Long page = 0L;
        Long count = 10L;

        when(newsRepository.findPostsBySubscriber(eq(userId), any(PageRequest.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            newsService.getPostsBySubscriber(userId, page, count);
        });

        assertEquals("Database error", exception.getCause().getMessage());
        verify(newsRepository, times(1)).findPostsBySubscriber(eq(userId), any(PageRequest.class));
    }
}