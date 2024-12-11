package devspace.devspaceback.models.Responses;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentaryResponse {

    private Double note;
    private String text;
}
