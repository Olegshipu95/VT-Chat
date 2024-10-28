package itmo.high_perf_sys.chat.dto.feed.response;
import itmo.high_perf_sys.chat.entity.Post;

import java.sql.Timestamp;
import java.util.UUID;

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
