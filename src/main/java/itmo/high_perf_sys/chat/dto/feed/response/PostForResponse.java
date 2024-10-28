package itmo.high_perf_sys.chat.dto.feed.response;
import itmo.high_perf_sys.chat.model.entity.Post;

import java.sql.Timestamp;

public class PostForResponse{
    private Long id;
    private Long userId;
    private String title;
    private String text;
    private byte[] images;
    private Timestamp postedTime;
    public PostForResponse(Post post){
        this.id = post.getId();
        this.userId = post.getUserId();
        this.title = post.getTitle();
        this.text = post.getText();
        this.images = post.getImages();
        this.postedTime = post.getPostedTime();
    }
}
