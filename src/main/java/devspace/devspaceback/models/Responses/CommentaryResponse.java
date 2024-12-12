package devspace.devspaceback.models.Responses;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentaryResponse {

    private Long id;
    private Double note;
    private String text;
}
