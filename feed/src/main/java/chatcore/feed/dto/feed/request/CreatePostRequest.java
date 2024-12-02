package chatcore.feed.dto.feed.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreatePostRequest(
        @NotNull(message = "user id cannot be null")
        UUID userId,
        @NotNull(message = "feed title cannot be null")
        String title,
        String text,
        byte[] images
) {
}
