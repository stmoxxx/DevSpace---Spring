package devspace.devspaceback.models.Responses;


import devspace.devspaceback.models.UserEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {

    private Long id;
    private String title;
    private UserEntity author;
    private String text;
    private byte[] postMedia;
    private boolean isPrivate;

}
