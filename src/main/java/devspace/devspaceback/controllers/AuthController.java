package devspace.devspaceback.controllers;


import devspace.devspaceback.models.RegistrationRequestEntity;
import devspace.devspaceback.security.AuthenticationRequest;
import devspace.devspaceback.security.AuthenticationResponse;
import devspace.devspaceback.services.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Tag(name="Authentication")
public class AuthController {

    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register (
            @RequestBody @Valid RegistrationRequestEntity request
            ){
        authenticationService.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @RequestBody @Valid AuthenticationRequest authenticationRequest
    ) {
       return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @GetMapping("/activation")
    public void confirm (
            @RequestParam String token
    ) throws MessagingException {
        authenticationService.activateAccount(token);
    }


    @GetMapping("/isgood")
    public String isGood(){
        return "all good";
    }
}
