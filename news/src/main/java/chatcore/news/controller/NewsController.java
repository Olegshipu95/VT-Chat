package chatcore.news.controller;

import chatcore.news.dto.response.PostForResponse;
import chatcore.news.service.NewsService;
import chatcore.news.utils.ErrorMessages;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/{userId}")
    public Flux<PostForResponse> getAllPostsByUserId(@NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
                                                     @PathVariable UUID userId) {
        return newsService.getPostsBySubscriber(userId)
                .onErrorResume(e -> Flux.error(new RuntimeException(ErrorMessages.ERROR_DB_REQUEST, e)));
    }
}
