package devspace.devspaceback.controllers;

import devspace.devspaceback.models.PostEntity;
import devspace.devspaceback.pagination.PageResponse;
import devspace.devspaceback.models.Requests.PostRequest;
import devspace.devspaceback.models.Responses.PostResponse;
import devspace.devspaceback.services.PostService;
import devspace.devspaceback.models.Responses.SavedPostsResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Post")
public class PostController {

    private final PostService postService;


    @GetMapping("/post/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable ("id") Long postId){
        List<PostEntity> posts = new ArrayList<>();
        return ResponseEntity.ok(postService.findById(postId));
    }

    @PostMapping("/post/save")
    public ResponseEntity<Long> newPost(
            @Valid @RequestBody PostRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(postService.savePost(request, connectedUser));
    }

    @GetMapping("/posts")
    public ResponseEntity<PageResponse<PostResponse>> findAllPosts (
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
            ){
        return ResponseEntity.ok(postService.findAllPosts(page, size, connectedUser));
    }

    @GetMapping("/author")
    public ResponseEntity<PageResponse<PostResponse>> findAllPostsByAuthor (
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(postService.findAllPostsByAuthor(page, size, connectedUser));

    }

    @GetMapping("/saved")
    public ResponseEntity<PageResponse<SavedPostsResponse>> findAllSavedPosts (
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(postService.findAllSavedPosts(page, size, connectedUser));

    }


    @PatchMapping("/private/{post-id}")
    public ResponseEntity<Long> updatePrivateStatus(
            @PathVariable("post-id") Long postId,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(postService.updatePrivateStatus(postId, connectedUser));
    }

    @PostMapping("/repost/{post-id}")
    public ResponseEntity<Long> repostPost(
            @PathVariable("post-id") Long postId,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(postService.repostPost(postId,connectedUser));
    }

    @PatchMapping("/repost/delete/{post-id}")
    public ResponseEntity<Long> deleteRepostedPost(
            @PathVariable("post-id") Long postId,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(postService.deleteRepostedPost(postId, connectedUser));
    }

    @PostMapping(value = "/images/{post-id}", consumes ="multipart/form-data")
    public ResponseEntity<?> uploadPostImages(
            @PathVariable("post-id") Long postId,
            @Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication connectedUser
    ){
        postService.uploadPostImages(file, connectedUser, postId);
        return ResponseEntity.accepted().build();
    }

}
