package devspace.devspaceback.services;

import devspace.devspaceback.exceptions.OperationNotPermittedException;
import devspace.devspaceback.models.CommentaryEntity;
import devspace.devspaceback.models.PostEntity;
import devspace.devspaceback.models.Requests.CommentaryRequest;
import devspace.devspaceback.models.Responses.CommentaryResponse;
import devspace.devspaceback.models.UserEntity;
import devspace.devspaceback.pagination.PageResponse;
import devspace.devspaceback.repositories.CommentaryRepository;
import devspace.devspaceback.repositories.post.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentaryService {

    private final PostRepository postRepository;
    private final CommentaryMapper commentaryMapper;
    private final CommentaryRepository commentaryRepository;

    public Long save(CommentaryRequest request, Authentication connectedUser) {
        PostEntity post = postRepository.findById(request.postId())
                .orElseThrow(() -> new EntityNotFoundException("No post with ID" + request.postId()));
        if (post.isPrivate()){
            throw new OperationNotPermittedException("You can not comment a private post");
        }
        CommentaryEntity commentary = commentaryMapper.toCommentary(request);

        return commentaryRepository.save(commentary).getId();
    }

    public PageResponse<CommentaryResponse> findAllCommentariesByPost(Long postId, int page, int size, Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page,size);
        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        Page<CommentaryEntity> commentaries = commentaryRepository.findAllByPostId(postId, pageable);
        List<CommentaryResponse> commentaryResponses = commentaries.stream().
                map(c -> commentaryMapper.toCommentaryResponse(c, user.getId()))
                .toList();
        return new PageResponse<>(
                commentaryResponses,
                commentaries.getNumber(),
                commentaries.getSize(),
                commentaries.getTotalElements(),
                commentaries.getTotalPages(),
                commentaries.isFirst(),
                commentaries.isLast()
        );
    }
}
