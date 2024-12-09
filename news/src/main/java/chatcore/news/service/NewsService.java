package chatcore.news.service;

import chatcore.news.repository.NewsRepository;
import org.springframework.stereotype.Service;
import chatcore.news.dto.response.PostForResponse;
import java.util.UUID;
import reactor.core.publisher.Flux;

@Service
public class NewsService {
    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public Flux<PostForResponse> getPostsBySubscriber(UUID userId, Long page, Long count) {
        int offset = page.intValue() * count.intValue();
        return newsRepository.findPostsBySubscriber(userId, offset, count.intValue())
                .map(PostForResponse::new);
    }
}
