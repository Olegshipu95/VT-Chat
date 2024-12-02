package chatcore.feed.dto.feed.response;
import chatcore.feed.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
public class PostForResponse{
    private UUID id;
    private UUID userId;
    private String title;
    private String text;
    private byte[] images;
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
