package devspace.devspaceback;

import devspace.devspaceback.repositories.role.RoleRepository;
import devspace.devspaceback.roles.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing  //  makes auditing possible on Entities
@EnableAsync
public class DevspaceBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevspaceBackApplication.class, args);
    }

    @Bean
	public CommandLineRunner runner(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByRoleName("USER").isEmpty()) {
				roleRepository.save(Role.builder().roleName("USER").build());
			}
		};
	}
}
