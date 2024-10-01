package devspace.devspaceback.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequestEntity {

    @NotEmpty(message = "The firstname is required")
    @NotBlank(message = "The firstname is required")
    private String firstname;
    @NotEmpty(message = "The lastname is required")
    @NotBlank(message = "The lastname is required")
    private String lastname;
    @Email(message = "Email is not formatted")
    @NotEmpty(message = "The email is required")
    @NotBlank(message = "The email is required")
    private String email;
    @NotEmpty(message = "The password is required")
    @NotBlank(message = "The password is required")
    private String password;
}
