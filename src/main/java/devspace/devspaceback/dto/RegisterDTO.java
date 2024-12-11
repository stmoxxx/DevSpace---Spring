package devspace.devspaceback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterDTO {

    private String name;
    private String lastname;
    private String nickname;
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 5, max = 256, message = "Password should contain between 5 and 256 symbols")
    private String password;


}
