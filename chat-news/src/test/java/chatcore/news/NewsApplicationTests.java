package chatcore.news;

import chatcore.news.dto.response.PostForResponse;
import chatcore.news.entity.Post;
import chatcore.news.entity.User;
import chatcore.news.repository.NewsRepository;
import chatcore.news.service.NewsService;
import chatcore.news.utils.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import reactor.test.StepVerifier;

import java.util.UUID;

public class NewsApplicationTests {

    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private NewsService newsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPostsBySubscriber_Success() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        Post post1 = new Post();
        Post post2 = new Post();
        post1.setUser(user);
        post2.setUser(user);
        when(newsRepository.findPostsBySubscriber(userId)).thenReturn(Flux.just(post1, post2));
        Flux<PostForResponse> result = newsService.getPostsBySubscriber(userId);
        assertNotNull(result);
        assertEquals(2, result.count().block());
        verify(newsRepository, times(1)).findPostsBySubscriber(userId);
    }

    @Test
    public void testGetPostsBySubscriber_Error() {
        UUID userId = UUID.randomUUID();
        when(newsRepository.findPostsBySubscriber(userId)).thenReturn(Flux.error(new RuntimeException(ErrorMessages.ERROR_DB_REQUEST)));
        Flux<PostForResponse> result = newsService.getPostsBySubscriber(userId);
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals(ErrorMessages.ERROR_DB_REQUEST))
                .verify();
        verify(newsRepository, times(1)).findPostsBySubscriber(userId);
    }
}