package itmo.high_perf_sys.chat.controller;

import itmo.high_perf_sys.chat.dto.customer.request.CreateUserAccountRequest;
import itmo.high_perf_sys.chat.dto.customer.request.UpdateUserInfoRequest;
import itmo.high_perf_sys.chat.dto.customer.response.GetUserInfoResponse;
import itmo.high_perf_sys.chat.dto.feed.request.CreateFeedRequest;
import itmo.high_perf_sys.chat.service.CustomerService;
import itmo.high_perf_sys.chat.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Long> createFeed(@RequestBody CreateFeedRequest createFeedRequest) {
        Long id = feedService.createFeed(createFeedRequest);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
