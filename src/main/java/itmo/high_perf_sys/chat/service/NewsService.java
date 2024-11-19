package itmo.high_perf_sys.chat.service;

import itmo.high_perf_sys.chat.dto.feed.response.FeedResponse;
import itmo.high_perf_sys.chat.dto.feed.response.PostForResponse;
import itmo.high_perf_sys.chat.entity.Post;
import itmo.high_perf_sys.chat.repository.NewsRepository;
import itmo.high_perf_sys.chat.utils.ErrorMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    public NewsService(NewsRepository newsRepository){
        this.newsRepository = newsRepository;
    }
    public FeedResponse getPostsBySubscriber(UUID userId, Long page, Long count) {
        try {
            Page<Post> pageOfPosts = newsRepository.findPostsBySubscriber(userId, PageRequest.of(page.intValue(), count.intValue()));
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
