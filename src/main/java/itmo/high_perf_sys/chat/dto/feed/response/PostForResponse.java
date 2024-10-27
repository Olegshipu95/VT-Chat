package itmo.high_perf_sys.chat.dto.feed.response;
import itmo.high_perf_sys.chat.entity.Post;
import itmo.high_perf_sys.chat.entity.User;

import java.sql.Timestamp;
import java.util.List;

public class PostForResponse{
    private Long id;
    private Long userId;
    private String title;
    private String text;
    private List<byte[]> images;
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
