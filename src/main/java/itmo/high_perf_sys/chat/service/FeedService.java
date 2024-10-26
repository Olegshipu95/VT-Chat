package itmo.high_perf_sys.chat.service;

import itmo.high_perf_sys.chat.dto.feed.request.CreateFeedRequest;
import itmo.high_perf_sys.chat.entity.Feed;
import itmo.high_perf_sys.chat.repository.FeedRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.UUID;

@Service
public class FeedService {
    private FeedRepository feedRepository;
    @Transactional
    public Long createFeed(CreateFeedRequest createFeedRequest) {
        Feed feed = new Feed();
        feed.setTitle(createFeedRequest.title());
        feed.setText(createFeedRequest.text());
        feed.setImages(createFeedRequest.images());
        feed.setPostedTime(new Timestamp(System.currentTimeMillis()));

        Feed savedFeed = feedRepository.save(feed);

        return savedFeed.getId();
    }
}
