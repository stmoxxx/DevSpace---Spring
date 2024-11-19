package devspace.devspaceback.models.media;


import devspace.devspaceback.models.PostEntity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@DiscriminatorValue("POST")
public class PostMedia extends Media{

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post_entity;

}
