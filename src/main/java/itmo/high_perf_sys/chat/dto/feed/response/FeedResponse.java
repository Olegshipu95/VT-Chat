package itmo.high_perf_sys.chat.dto.feed.response;

import java.util.List;

public record FeedResponse(
        List<PostForResponse> feed
) {
}
