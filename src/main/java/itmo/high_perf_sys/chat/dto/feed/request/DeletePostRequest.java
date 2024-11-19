package itmo.high_perf_sys.chat.dto.feed.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeletePostRequest(
        @NotNull(message = "user id cannot be null")
        @JsonProperty("user_id")
        UUID userId,
        @NotNull(message = "feed id cannot be null")
        @JsonProperty("feed_id")
        UUID feedId
) {
}
