package itmo.high_perf_sys.chat.service;

import itmo.high_perf_sys.chat.dto.feed.request.CreatePostRequest;
import itmo.high_perf_sys.chat.dto.feed.request.DeletePostRequest;
import itmo.high_perf_sys.chat.dto.feed.response.FeedResponse;
import itmo.high_perf_sys.chat.dto.feed.response.PostForResponse;
import itmo.high_perf_sys.chat.entity.Post;
import itmo.high_perf_sys.chat.entity.customer.UserAccount;
import itmo.high_perf_sys.chat.repository.FeedRepository;
import itmo.high_perf_sys.chat.repository.UserAccountRepository;
import itmo.high_perf_sys.chat.utils.ErrorMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FeedService {
    private final FeedRepository feedRepository;
    private final UserAccountRepository userAccountRepository; // Репозиторий для UserAccount
    public FeedService(FeedRepository feedRepository, UserAccountRepository userAccountRepository){
        this.feedRepository = feedRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Transactional
    public UUID createFeed(CreatePostRequest createPostRequest) {

        UserAccount user = userAccountRepository.findById(createPostRequest.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setId(UUID.randomUUID());
        post.setUser(user);
        post.setTitle(createPostRequest.title());
        post.setText(createPostRequest.text());
        post.setImages(createPostRequest.images());
        post.setPostedTime(new Timestamp(System.currentTimeMillis()));

        feedRepository.save(post);

        return post.getId();
    }

    public void deletePost(DeletePostRequest request){
        Optional<Post> post = feedRepository.findById(request.feedId());
        if(post.isEmpty()) throw new RuntimeException(ErrorMessages.POST_NOT_FOUND);
        if(Objects.equals(post.get().getUser().getId(), request.userId())){
            feedRepository.deleteById(request.feedId());
        } else {
            throw new RuntimeException(ErrorMessages.NO_PERMISSION);
        }
    }

    public FeedResponse getFeedByUserId(Long userId, Long page, Long count){
        try {
            Page<Post> pageOfPosts = feedRepository.findByUserId(userId, PageRequest.of(page.intValue(), count.intValue()));
            return new FeedResponse(
                    pageOfPosts.stream()
                            .map(PostForResponse::new)
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.ERROR_DB_REQUEST, e);
        }
    }
}
