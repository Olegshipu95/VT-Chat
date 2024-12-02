package chatcore.feed.dto.feed.response;

import java.util.List;

public record FeedResponse(
        List<PostForResponse> feed
) {
}
