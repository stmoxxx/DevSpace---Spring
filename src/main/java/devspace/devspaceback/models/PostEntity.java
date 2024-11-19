package devspace.devspaceback.models;

import devspace.devspaceback.models.media.PostMedia;
import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "post")
public class PostEntity extends DefaultEntityTools {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @CreatedBy
    private UserEntity author; // Link to the user who created the post

    @OneToMany(mappedBy = "post",  fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentaryEntity> commentaries;

    @OneToMany(mappedBy = "post_entity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostMedia> postMedia;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "statistic_id", referencedColumnName = "statisticId")
    private Statistic statistic; // Post statistics

    private String text;


}

