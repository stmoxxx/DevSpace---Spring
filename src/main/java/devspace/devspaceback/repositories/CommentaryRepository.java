package devspace.devspaceback.repositories;

import devspace.devspaceback.models.CommentaryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentaryRepository extends JpaRepository<CommentaryEntity, Long> {

    @Query("""
            SELECT commentary
            FROM CommentaryEntity commentary
            WHERE commentary.post.id = :postId
            """)
    Page<CommentaryEntity> findAllByPostId(Long postId, Pageable pageable);

}
