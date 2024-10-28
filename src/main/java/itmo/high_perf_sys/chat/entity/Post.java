package itmo.high_perf_sys.chat.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import itmo.high_perf_sys.chat.utils.ErrorMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Table(name = "posts")
public class Post {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = ErrorMessages.CHAT_CANNOT_BE_NULL)
    private User user;
    @Getter
    @Column(name = "title")
    private String title;
    @Getter
    @Column(name = "text", nullable = false)
    private String text;
    @Getter
    @Column(name = "images")
    private byte[] images;
    @Getter
    @Column(name = "posted_time", nullable = false)
    private Timestamp postedTime;

    @Getter
    @ElementCollection
    @CollectionTable(name = "post_likes", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "user_id")
    private Set<Long> likes = new HashSet<>();

    @Getter
    @ElementCollection
    @CollectionTable(name = "post_dislikes", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "user_id")
    private Set<Long> dislikes = new HashSet<>();

    public void setLikes(Set<Long> likes) {
        this.likes = likes;
    }

    public void setDislikes(Set<Long> dislikes) {
        this.dislikes = dislikes;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return user.getId();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }

    public void setPostedTime(Timestamp postedTime) {
        this.postedTime = postedTime;
    }
}
