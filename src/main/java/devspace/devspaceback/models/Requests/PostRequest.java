package devspace.devspaceback.models.Requests;

import devspace.devspaceback.models.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PostRequest(
        Long id,
        @NotNull(message = "100")
        @NotEmpty(message = "100")
        String text,
        @NotNull(message = "102")
        @NotEmpty(message = "102")
        String title,
        boolean isPrivate

) {


}
