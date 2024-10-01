package devspace.devspaceback.repositories.role;

import devspace.devspaceback.roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String role);

}
