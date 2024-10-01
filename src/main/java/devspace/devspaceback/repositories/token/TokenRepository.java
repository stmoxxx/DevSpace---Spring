package devspace.devspaceback.repositories.token;

import devspace.devspaceback.token.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<UserToken, Long> {

    Optional<UserToken> findByToken (String token);
}
