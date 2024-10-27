package itmo.high_perf_sys.chat.controller;

import itmo.high_perf_sys.chat.dto.feed.request.CreatePostRequest;
import itmo.high_perf_sys.chat.dto.feed.request.DeletePostRequest;
import itmo.high_perf_sys.chat.entity.Post;
import itmo.high_perf_sys.chat.service.FeedService;
import itmo.high_perf_sys.chat.utils.ErrorMessages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {
    private final FeedService feedService;

    @Autowired
    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @PostMapping("/add")
    public ResponseEntity<Long> createPost(@RequestBody CreatePostRequest createPostRequest) {
        Long id = feedService.createFeed(createPostRequest);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deletePost(@RequestBody DeletePostRequest request){
        try{
            feedService.deletePost(request);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
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
            List<Post> response = feedService.getFeedByUserId(userId, pageNumber, count);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
