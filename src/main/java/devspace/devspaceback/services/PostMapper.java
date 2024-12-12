package devspace.devspaceback.services;

import devspace.devspaceback.file.FileUtils;
import devspace.devspaceback.history.SavedPosts;
import devspace.devspaceback.models.PostEntity;
import devspace.devspaceback.models.Requests.PostRequest;
import devspace.devspaceback.models.Responses.PostResponse;
import devspace.devspaceback.models.Responses.SavedPostsResponse;
import devspace.devspaceback.models.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class PostMapper {
    public PostEntity toPost(PostRequest request, UserEntity author) {
        return PostEntity.builder()
                .id(request.id())
                .title(request.title())
                .text(request.text())
                .author(author)
                .build();

    }

    public PostResponse toPostResponse(PostEntity postEntity) {
        return PostResponse.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .text(postEntity.getText())
                .author(postEntity.getAuthor())
                .isPrivate(postEntity.isPrivate())
                .postMedia(FileUtils.readFileFromLocation(postEntity.getPostMedia()))
                .build();
    }

    public SavedPostsResponse toSavedPostResponse(SavedPosts savedPosts) {
        return SavedPostsResponse.builder()
                .id(savedPosts.getPost().getId())
                .title(savedPosts.getPost().getTitle())
                .text(savedPosts.getPost().getText())
                .author(savedPosts.getPost().getAuthor())
                .isPrivate(savedPosts.getPost().isPrivate())
                .build();
    }
}
