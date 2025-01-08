package chatcore.feed.controller;
import chatcore.feed.dto.feed.request.CreatePostRequest;
import chatcore.feed.dto.feed.request.DeletePostRequest;
import chatcore.feed.dto.feed.response.FeedResponse;
import chatcore.feed.service.FeedService;
import chatcore.feed.utils.ErrorMessages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/feed")
public class FeedController {
    private final FeedService feedService;

    @Autowired
    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UUID> createPost(@RequestBody CreatePostRequest createPostRequest) {
        UUID id = feedService.createFeed(createPostRequest);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<?> deletePost(@RequestBody DeletePostRequest request){
        try{
            feedService.deletePost(request);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAllPostsByUserId(@NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
                                                    @PathVariable UUID userId,
                                                    @NotNull(message = ErrorMessages.PAGE_CANNOT_BE_NULL)
                                                    @Min(value = 0, message = ErrorMessages.PAGE_CANNOT_BE_NEGATIVE)
                                                    @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Long pageNumber,
                                                    @NotNull(message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NULL)
                                                    @Min(value = 0, message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NEGATIVE)
                                                    @RequestParam(value = "countMessagesOnPage", required = false, defaultValue = "20") Long count) {
        try {
            FeedResponse response = feedService.getFeedByUserId(userId, pageNumber, count);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
