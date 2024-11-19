package devspace.devspaceback.models;

import devspace.devspaceback.models.media.CommentaryMedia;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "commentary")
public class CommentaryEntity extends DefaultEntityTools{

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post; // Indicates the post to which the comment relates

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity author; // Indicates the author of the comment

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private CommentaryEntity parentComment; // For nested comments

    private String text;

    @OneToMany(mappedBy = "commentary_entity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommentaryMedia> commentaryMedia;

}
