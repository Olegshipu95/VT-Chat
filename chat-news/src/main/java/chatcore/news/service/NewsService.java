package chatcore.news.service;

import chatcore.news.dto.response.PostForResponse;
import chatcore.news.repository.NewsRepository;
import chatcore.news.utils.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;


@Service
public class NewsService {

    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public Flux<PostForResponse> getPostsBySubscriber(UUID userId) {
        return newsRepository.findPostsBySubscriber(userId)
                .map(PostForResponse::new)
                .onErrorResume(e -> Flux.error(new RuntimeException(ErrorMessages.ERROR_DB_REQUEST, e)));
    }
}
