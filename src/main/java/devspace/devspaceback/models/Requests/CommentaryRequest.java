package devspace.devspaceback.models.Requests;

import jakarta.validation.constraints.*;

public record CommentaryRequest(

        @Positive(message = "200")
        @Min(value = 0, message = "201")
        @Max(value = 5, message = "202")
        Double note,
        @NotNull(message = "203")
        @NotEmpty(message = "203")
        @NotBlank(message = "203")
        String text,
        @NotNull(message = "204")
        Long postId
) {



}
