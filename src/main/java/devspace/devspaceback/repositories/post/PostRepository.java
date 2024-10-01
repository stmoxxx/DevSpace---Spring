package devspace.devspaceback.repositories.post;

import devspace.devspaceback.models.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
