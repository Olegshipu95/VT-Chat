package chatcore.feed;

import chatcore.feed.controller.FeedController;
import chatcore.feed.dto.feed.request.CreatePostRequest;
import chatcore.feed.dto.feed.request.DeletePostRequest;
import chatcore.feed.dto.feed.response.FeedResponse;
import chatcore.feed.service.FeedService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FeedControllerTest {

    @InjectMocks
    private FeedController feedController;

    @Mock
    private FeedService feedService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePost_Success() {
        CreatePostRequest createPostRequest = new CreatePostRequest(UUID.randomUUID(), "Sample Title", "Sample Text", null);
        UUID postId = UUID.randomUUID();
        when(feedService.createFeed(createPostRequest)).thenReturn(postId);
        ResponseEntity<UUID> response = feedController.createPost(createPostRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(postId, response.getBody());
        verify(feedService, times(1)).createFeed(createPostRequest);
    }

    @Test
    public void testDeletePost_Success() {
        DeletePostRequest deletePostRequest = new DeletePostRequest(UUID.randomUUID(), UUID.randomUUID());
        ResponseEntity<?> response = feedController.deletePost(deletePostRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(feedService, times(1)).deletePost(deletePostRequest);
    }

    @Test
    public void testDeletePost_Error() {
        DeletePostRequest deletePostRequest = new DeletePostRequest(UUID.randomUUID(), UUID.randomUUID());
        doThrow(new RuntimeException("Error deleting post")).when(feedService).deletePost(deletePostRequest);
        ResponseEntity<?> response = feedController.deletePost(deletePostRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error deleting post", response.getBody());
        verify(feedService, times(1)).deletePost(deletePostRequest);
    }

    @Test
    public void testGetAllPostsByUserId_Success() {
        UUID userId = UUID.randomUUID();
        long pageNumber = 0;
        long count = 20;
        FeedResponse feedResponse = new FeedResponse(List.of()); // Создайте список PostForResponse, если необходимо
        when(feedService.getFeedByUserId(userId, pageNumber, count)).thenReturn(feedResponse);
        ResponseEntity<?> response = feedController.getAllPostsByUserId(userId, pageNumber, count);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(feedResponse, response.getBody());
        verify(feedService, times(1)).getFeedByUserId(userId, pageNumber, count);
    }

    @Test
    public void testGetAllPostsByUserId_Error() {
        UUID userId = UUID.randomUUID();
        long pageNumber = 0;
        long count = 20;
        when(feedService.getFeedByUserId(userId, pageNumber, count)).thenThrow(new RuntimeException("Error fetching posts"));
        ResponseEntity<?> response = feedController.getAllPostsByUserId(userId, pageNumber, count);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error fetching posts", response.getBody());
        verify(feedService, times(1)).getFeedByUserId(userId, pageNumber, count);
    }
}