package devspace.devspaceback.services;

import devspace.devspaceback.models.RegistrationRequestEntity;
import devspace.devspaceback.models.UserEntity;
import devspace.devspaceback.repositories.role.RoleRepository;
import devspace.devspaceback.repositories.token.TokenRepository;
import devspace.devspaceback.repositories.user.UserRepository;
import devspace.devspaceback.security.AuthenticationRequest;
import devspace.devspaceback.security.AuthenticationResponse;
import devspace.devspaceback.token.UserToken;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public void register(@Valid RegistrationRequestEntity request) {
        var userRole = roleRepository.findByRoleName("USER").orElseThrow(() -> new IllegalStateException("Role USER is not initialized"));

        var user = UserEntity.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendVerificationEmail(user);
    }

    private void sendVerificationEmail(UserEntity user) {
        var newToken = generateAndSaveValidationToken(user);

        emailService.sendVerificationEmail(
                user.getEmail(),
                user.getFullName(),
                newToken
        );
    }

//    @Transactional
    public void activateAccount(String token) throws MessagingException {
        UserToken savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())){
            sendVerificationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token is sent to the same email address");
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    private String generateAndSaveValidationToken(UserEntity user) {
        // generate a token
        String generatedToken = generateActivationToken(6);
        var token = UserToken.builder()
                .token(generatedToken)
                .validatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(20))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((UserEntity)auth.getPrincipal());
        claims.put("fullName", user.getFullName());
        var jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
    private String generateActivationToken (int length) {

        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i <length; i++){
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }



}
