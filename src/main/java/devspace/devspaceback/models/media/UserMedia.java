package devspace.devspaceback.models.media;

import devspace.devspaceback.models.UserEntity;
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
//@DiscriminatorValue("USER")
public class UserMedia extends Media{

        @ManyToOne
        @JoinColumn(name = "user_id")
        private UserEntity user_entity;

}
