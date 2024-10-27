package itmo.high_perf_sys.chat.dto.feed.request;

import jakarta.validation.constraints.NotNull;

public record DeletePostRequest(
        @NotNull(message = "user id cannot be null")
        Long userId,
        @NotNull(message = "feed id cannot be null")
        Long feedId
) {
}
