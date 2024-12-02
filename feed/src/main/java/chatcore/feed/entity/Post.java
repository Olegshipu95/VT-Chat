package chatcore.feed.entity;

import chatcore.feed.utils.ErrorMessages;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @Getter
    @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
    @Column(name = "id")
    private UUID id;
    @ManyToOne
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

}