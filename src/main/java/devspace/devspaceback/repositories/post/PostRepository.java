package devspace.devspaceback.repositories.post;

import devspace.devspaceback.models.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<PostEntity, Long>, JpaSpecificationExecutor<PostEntity> {
    @Query("""
            SELECT post
            FROM PostEntity post
            WHERE post.isPrivate = false
            AND post.author.id != :userId
            """)
    Page<PostEntity> findAllDisplayablePosts(Pageable pageable, Long userId);
}
