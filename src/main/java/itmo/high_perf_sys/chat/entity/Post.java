package itmo.high_perf_sys.chat.model.entity;

import java.sql.Timestamp;

import itmo.high_perf_sys.chat.utils.ErrorMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = ErrorMessages.CHAT_CANNOT_BE_NULL)
    private User user;
    @Column(name = "title")
    private String title;
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "images")
    private byte[] images;
    @Column(name = "posted_time", nullable = false)
    private Timestamp postedTime;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return user.getId();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getImages() {
        return images;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }

    public Timestamp getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(Timestamp postedTime) {
        this.postedTime = postedTime;
    }
}
