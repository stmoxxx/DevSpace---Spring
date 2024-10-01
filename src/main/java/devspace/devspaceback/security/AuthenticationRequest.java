package devspace.devspaceback.security;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {


    @Email(message = "Email is not formatted")
    @NotEmpty(message = "The email is required")
    @NotBlank(message = "The email is required")
    private String email;
    @NotEmpty(message = "The password is required")
    @NotBlank(message = "The password is required")
    private String password;

}
