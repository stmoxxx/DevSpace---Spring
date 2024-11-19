package devspace.devspaceback.models.media;

import devspace.devspaceback.models.CommentaryEntity;
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
//@DiscriminatorValue("COMMENT")
public class CommentaryMedia extends Media{

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentaryEntity commentary_entity;
}
