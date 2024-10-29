package itmo.high_perf_sys.chat.controller;

import itmo.high_perf_sys.chat.dto.feed.response.FeedResponse;
import itmo.high_perf_sys.chat.service.NewsService;
import itmo.high_perf_sys.chat.utils.ErrorMessages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService){
        this.newsService = newsService;
    }
    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllPostsByUserId(@NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
                                                 @PathVariable Long userId,
                                                 @NotNull(message = ErrorMessages.PAGE_CANNOT_BE_NULL)
                                                 @Min(value = 0, message = ErrorMessages.PAGE_CANNOT_BE_NEGATIVE)
                                                 @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Long pageNumber,
                                                 @NotNull(message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NULL)
                                                 @Min(value = 0, message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NEGATIVE)
                                                 @RequestParam(value = "countMessagesOnPage", required = false, defaultValue = "20") Long count) {
        try {
            FeedResponse response = newsService.getPostsBySubscriber(userId, pageNumber, count);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
