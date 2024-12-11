package devspace.devspaceback.repositories.post;

import devspace.devspaceback.models.media.PostMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostMediaRepository extends JpaRepository<PostMedia, Long> {

    Optional<PostMedia> findByName(String name);
}
