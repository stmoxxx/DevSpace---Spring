package devspace.devspaceback.services;

import devspace.devspaceback.models.CommentaryEntity;
import devspace.devspaceback.models.PostEntity;
import devspace.devspaceback.models.Requests.CommentaryRequest;
import devspace.devspaceback.models.Responses.CommentaryResponse;
import org.springframework.stereotype.Service;

@Service
public class CommentaryMapper {


    public CommentaryEntity toCommentary(CommentaryRequest request) {
        return CommentaryEntity.builder()
                .note(request.note())
                .text(request.text())
                .post(PostEntity.builder()
                        .id(request.postId())
                        .isPrivate(false)
                        .build()
                )
                .build();
    }

    public CommentaryResponse toCommentaryResponse(CommentaryEntity commentary, long id) {
        return CommentaryResponse.builder()
                .note(commentary.getNote())
                .text(commentary.getText())
                .build();
    }
}
