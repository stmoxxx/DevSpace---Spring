package devspace.devspaceback.models.media;


import devspace.devspaceback.models.PostEntity;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
//@DiscriminatorValue("POST")
public class PostMedia extends Media{

    private String name;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post_entity;

}
