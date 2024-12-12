package devspace.devspaceback.services;

import devspace.devspaceback.exceptions.OperationNotPermittedException;
import devspace.devspaceback.history.SavedPosts;
import devspace.devspaceback.history.SavedPostsRepository;
import devspace.devspaceback.models.PostEntity;
import devspace.devspaceback.models.Requests.PostRequest;
import devspace.devspaceback.models.Responses.PostResponse;
import devspace.devspaceback.models.Responses.SavedPostsResponse;
import devspace.devspaceback.models.UserEntity;
import devspace.devspaceback.pagination.PageResponse;
import devspace.devspaceback.repositories.post.PostRepository;
import devspace.devspaceback.repositories.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final SavedPostsRepository savedPostsRepository;
    private final PostMapper postMapper;
    private final FileStorageService  fileStorageService;
    private final UserRepository userRepository;

//    public Long savePost(PostRequest request, Authentication connectedUser) {
//        UserEntity user = (UserEntity) connectedUser.getPrincipal();
//        PostEntity post = postMapper.toPost(request);
//        post.setAuthor(user);
//        return postRepository.save(post).getId();
//    }

    @Transactional
    public Long savePost(PostRequest request, Authentication connectedUser) {
        Object principal = connectedUser.getPrincipal();
        if (principal instanceof UserEntity) {
            UserEntity user = (UserEntity) principal;
            PostEntity post = postMapper.toPost(request, user);
            return postRepository.save(post).getId();
        } else {
            throw new IllegalStateException("Expected UserEntity, but got: " + principal.getClass().getName());
        }
    }


    public PostResponse findById (Long postId){
        return postRepository.findById(postId)
                .map(postMapper::toPostResponse)
                .orElseThrow(() -> new EntityNotFoundException("Post with ID: " + postId + "doesn't exist"));
    }

    @Transactional
    public PageResponse<PostResponse> findAllPosts(int page, int size, Authentication connectedUser) {
        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        System.out.println("User ID: " + user.getId());
        System.out.println("Page: " + page + ", Size: " + size);

        Page<PostEntity> posts = postRepository.findAllDisplayablePosts(pageable, user.getId());

        // Логирование результата запроса
        System.out.println("Found posts: " + posts.getContent().size());

        List<PostResponse> postResponse = posts.stream()
                .map(postMapper::toPostResponse)
                .toList();
        return new PageResponse<>(
                postResponse,
                posts.getNumber(),
                posts.getSize(),
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.isFirst(),
                posts.isLast()
        );
    }

    @Transactional
    public PageResponse<PostResponse> findAllPostsByAuthor(int page, int size, Authentication connectedUser) {
        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<PostEntity> posts = postRepository.findAll(PostSpecification.withAuthorId(user.getId()),  pageable);

        List<PostResponse> postResponse =  posts.stream()
                .map(postMapper::toPostResponse)
                .toList();
        return new PageResponse<>(
                postResponse,
                posts.getNumber(),
                posts.getSize(),
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.isFirst(),
                posts.isLast()
        );
    }

    @Transactional
    public PageResponse<SavedPostsResponse> findAllSavedPosts(int page, int size, Authentication connectedUser) {
        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<SavedPosts> allSavedPosts = savedPostsRepository.findAllSavedPosts(pageable, user.getId());
        List<SavedPostsResponse> savedPostsResponses = allSavedPosts.stream()
                .map(postMapper::toSavedPostResponse)
                .toList();
        return new PageResponse<>(
                savedPostsResponses,
                allSavedPosts.getNumber(),
                allSavedPosts.getSize(),
                allSavedPosts.getTotalElements(),
                allSavedPosts.getTotalPages(),
                allSavedPosts.isFirst(),
                allSavedPosts.isLast()
        );
    }

    public Long updatePrivateStatus(Long postId, Authentication connectedUser) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("No post with ID" + postId));
        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        if (!Objects.equals(post.getAuthor().getId(),  user.getId()))
            throw new OperationNotPermittedException("You cannot update posts status");
        post.setPrivate(!post.isPrivate());
        postRepository.save(post);
        return postId;
    }

    @Transactional
    public Long repostPost(Long postId, Authentication connectedUser) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("No post with ID" + postId));
        if (post.isPrivate()){
            throw new OperationNotPermittedException("Requested post cannot be reposted, since it private");
        }
        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        if (Objects.equals(post.getAuthor().getId(),  user.getId()))
            throw new OperationNotPermittedException("You cannot repost your own post");

        SavedPosts savedPosts = SavedPosts.builder()
                .user(user)
                .post(post)
                .build();
        return savedPostsRepository.save(savedPosts).getId();
    }

    public Long deleteRepostedPost(Long postId, Authentication connectedUser) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("No post with ID" + postId));
        if (post.isPrivate()){
            throw new OperationNotPermittedException("Requested post cannot be reposted, since it private");
        }
        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        if (Objects.equals(post.getAuthor().getId(),  user.getId()))
            throw new OperationNotPermittedException("You cannot repost or delete repost of your own post");
        SavedPosts savedPosts = savedPostsRepository.findByPostIdAndUserId(postId, user.getId())
                .orElseThrow(() -> new OperationNotPermittedException("You did not own this post"));
        return savedPostsRepository.save(savedPosts).getId();
    }

    public void uploadPostImages(MultipartFile file, Authentication connectedUser, Long postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("No post with ID" + postId));
        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        var postImage = fileStorageService.saveFile(file, user.getId());
        post.setPostMedia(postImage);
        postRepository.save(post);
    }

//    public Long newPost(CreatePostDto dto, Authentication connectedUser){
//        boolean  isPrivate = dto.getIsPrivate().equals("true");
//        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
//
//        var post = PostEntity.builder()
//                .text(dto.getText())
//                .isPrivate(isPrivate)
//                .build();
//        postRepository.save(post);
//        fileStorageService.saveFile()
//
//        return post.getId();
//    }
}
