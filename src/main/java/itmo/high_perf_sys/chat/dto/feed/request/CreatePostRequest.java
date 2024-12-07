package itmo.high_perf_sys.chat.dto.feed.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreatePostRequest(
        @NotNull(message = "user id cannot be null")
        @JsonProperty("user_id")
        UUID userId,
        @NotNull(message = "feed title cannot be null")
        @JsonProperty("title")
        String title,

        @JsonProperty("text")
        String text,
        @JsonProperty("images")
        byte[] images
) {
}
