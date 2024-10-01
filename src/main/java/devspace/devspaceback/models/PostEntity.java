package devspace.devspaceback.models;

import lombok.Data;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String author;
    private String text;

    //   private String authorImage;
    //    private LocalDateTime date;
    //    private List<PostImages> images;
    //    private List<PostCommentaries> commentaries;
    //    private List<PostStatistic> statistic;
}

