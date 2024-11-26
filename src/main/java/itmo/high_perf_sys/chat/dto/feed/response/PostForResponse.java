package itmo.high_perf_sys.chat.dto.feed.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.high_perf_sys.chat.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
public class PostForResponse{
    private UUID id;
    @JsonProperty("user_id")
    private UUID userId;
    private String title;
    private String text;
    private byte[] images;
    @JsonProperty("posted_time")
    private Timestamp postedTime;

    public PostForResponse(Post post){
        this.id = post.getId();
        this.userId = post.getUser().getId();
        this.title = post.getTitle();
        this.text = post.getText();
        this.images = post.getImages();
        this.postedTime = post.getPostedTime();
    }
}
