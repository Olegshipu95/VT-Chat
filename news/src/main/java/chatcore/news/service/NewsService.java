package chatcore.news.service;



import chatcore.news.dto.response.FeedResponse;
import chatcore.news.dto.response.PostForResponse;
import chatcore.news.entity.Post;
import chatcore.news.repository.NewsRepository;
import chatcore.news.utils.ErrorMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class NewsService {
    private NewsRepository newsRepository;
    public FeedResponse getPostsBySubscriber(Long userId, Long page, Long count) {
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
