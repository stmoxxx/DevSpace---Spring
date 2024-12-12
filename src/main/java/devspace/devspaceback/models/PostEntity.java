package devspace.devspaceback.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Builder
@Setter
@Table(name = "post")
public class PostEntity extends DefaultEntityTools{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UserEntity author;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    @CreatedBy
//    private UserEntity author; // Link to the user who created the post

    @OneToMany(mappedBy = "post",  fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentaryEntity> commentaries;

//    @OneToMany(mappedBy = "post_entity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private String postMedia;
    //private List<PostMedia> postMedia;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "statistic_id", referencedColumnName = "statisticId")
    private Statistic statistic; // Post statistics

    private String text;

    private String title;

    private boolean isPrivate;

}

