package chatcore.news.controller;

import chatcore.news.dto.response.PostForResponse;
import chatcore.news.service.NewsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping(value = "/subscribed/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<PostForResponse> getPostsBySubscriber(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") Long page,
            @RequestParam(defaultValue = "10") Long count) {
        return newsService.getPostsBySubscriber(userId, page, count);
    }
}
