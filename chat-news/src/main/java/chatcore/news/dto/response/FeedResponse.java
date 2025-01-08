package chatcore.news.dto.response;

import java.util.List;

public record FeedResponse(
        List<PostForResponse> feed
) {
}
