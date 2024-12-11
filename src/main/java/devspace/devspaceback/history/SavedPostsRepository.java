package devspace.devspaceback.history;

import devspace.devspaceback.models.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SavedPostsRepository extends JpaRepository<SavedPosts, Long> {
    @Query("""
            SELECT post
            FROM SavedPosts post
            WHERE post.user.id = :userId
            """)
    Page<SavedPosts> findAllSavedPosts(Pageable pageable, Long userId);

    @Query("""
            SELECT transaction
            FROM SavedPosts transaction
            WHERE transaction.user.id = :userId
            AND transaction.post.id  = :postId
            """)
    Optional<SavedPosts> findByPostIdAndUserId(Long postId, Long userId);

}
