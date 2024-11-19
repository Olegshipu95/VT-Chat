package itmo.high_perf_sys.chat.service;

import itmo.high_perf_sys.chat.dto.feed.request.CreatePostRequest;
import itmo.high_perf_sys.chat.dto.feed.request.DeletePostRequest;
import itmo.high_perf_sys.chat.dto.feed.response.FeedResponse;
import itmo.high_perf_sys.chat.entity.Post;
import itmo.high_perf_sys.chat.entity.User;
import itmo.high_perf_sys.chat.repository.FeedRepository;
import itmo.high_perf_sys.chat.repository.UserRepository;
import jdk.jfr.Timestamp;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class FeedServiceTest {

    private FeedRepository feedRepository;
    private UserRepository userRepository;
    private FeedService feedService;

    @BeforeEach
    void setUp() {
        feedRepository = mock(FeedRepository.class);
        userRepository = mock(UserRepository.class);
        feedService = new FeedService(feedRepository, userRepository);
    }

    @Test
    public void testCreateFeed() {
        UUID userId = UUID.randomUUID();
        CreatePostRequest request = mock(CreatePostRequest.class);

        when(request.userId()).thenReturn(userId);
        when(request.title()).thenReturn("Test Title");
        when(request.text()).thenReturn("Test Text");
        when(request.images()).thenReturn(List.of("image1.png", "image2.png"));

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UUID postId = feedService.createFeed(request);

        assertNotNull(postId);
        verify(userRepository, times(1)).findById(userId);
        verify(feedRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void testCreateFeedThrowsWhenUserNotFound() {
        UUID userId = UUID.randomUUID();
        CreatePostRequest request = mock(CreatePostRequest.class);

        when(request.userId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> feedService.createFeed(request));
        verify(userRepository, times(1)).findById(userId);
        verify(feedRepository, never()).save(any(Post.class));
    }

    @Test
    public void testDeletePost() {
        UUID postId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        DeletePostRequest request = mock(DeletePostRequest.class);

        when(request.feedId()).thenReturn(postId);
        when(request.userId()).thenReturn(userId);

        User user = new User();
        user.setId(userId);

        Post post = new Post();
        post.setId(postId);
        post.setUser(user);

        when(feedRepository.findById(postId)).thenReturn(Optional.of(post));

        feedService.deletePost(request);

        verify(feedRepository, times(1)).findById(postId);
        verify(feedRepository, times(1)).deleteById(postId);
    }

    @Test
    public void testDeletePostThrowsWhenPostNotFound() {
        UUID postId = UUID.randomUUID();
        DeletePostRequest request = mock(DeletePostRequest.class);

        when(request.feedId()).thenReturn(postId);
        when(feedRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> feedService.deletePost(request));
        verify(feedRepository, times(1)).findById(postId);
        verify(feedRepository, never()).deleteById(postId);
    }

    @Test
    public void testDeletePostThrowsWhenNoPermission() {
        UUID postId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID anotherUserId = UUID.randomUUID();
        DeletePostRequest request = mock(DeletePostRequest.class);

        when(request.feedId()).thenReturn(postId);
        when(request.userId()).thenReturn(userId);

        User anotherUser = new User();
        anotherUser.setId(anotherUserId);

        Post post = new Post();
        post.setId(postId);
        post.setUser(anotherUser);

        when(feedRepository.findById(postId)).thenReturn(Optional.of(post));

        assertThrows(RuntimeException.class, () -> feedService.deletePost(request));
        verify(feedRepository, times(1)).findById(postId);
        verify(feedRepository, never()).deleteById(postId);
    }

    @Test
    public void testGetFeedByUserId() {
        UUID userId = UUID.randomUUID();
        int page = 0;
        int count = 10;

        User user = new User();
        user.setId(userId);

        Post post1 = new Post();
        post1.setId(UUID.randomUUID());
        post1.setUser(user);
        post1.setTitle("Post 1");
        post1.setText("Text 1");
        post1.setPostedTime(new Timestamp(System.currentTimeMillis()));

        Post post2 = new Post();
        post2.setId(UUID.randomUUID());
        post2.setUser(user);
        post2.setTitle("Post 2");
        post2.setText("Text 2");
        post2.setPostedTime(new Timestamp(System.currentTimeMillis()));
        Page<Post> mockPage = new PageImpl<>(List.of(post1, post2));

        when(feedRepository.findByUserId(eq(userId), any(PageRequest.class))).thenReturn(mockPage);

        FeedResponse response = feedService.getFeedByUserId(userId, (long) page, (long) count);

        assertNotNull(response);
        assertEquals(2, response.feed().size());
        verify(feedRepository, times(1)).findByUserId(eq(userId), any(PageRequest.class));
    }

    @Test
    public void testGetFeedByUserIdThrowsExceptionOnDbError() {
        UUID userId = UUID.randomUUID();
        int page = 0;
        int count = 10;

        when(feedRepository.findByUserId(eq(userId), any(PageRequest.class)))
                .thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> feedService.getFeedByUserId(userId, (long) page, (long) count));
        verify(feedRepository, times(1)).findByUserId(eq(userId), any(PageRequest.class));
    }
}