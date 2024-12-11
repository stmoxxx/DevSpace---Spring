package devspace.devspaceback.controllers;

import devspace.devspaceback.pagination.PageResponse;
import devspace.devspaceback.models.Requests.CommentaryRequest;
import devspace.devspaceback.models.Responses.CommentaryResponse;
import devspace.devspaceback.services.CommentaryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("commentaries")
@RequiredArgsConstructor
@Tag(name = "Commentary")
public class CommentaryController {

    private final CommentaryService commentaryService;

    @PostMapping("")
    public ResponseEntity<Long> saveCommentary(
            @Valid @RequestBody CommentaryRequest request,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(commentaryService.save(request, connectedUser));
    }

    @GetMapping("/post/{post-id}")
    public ResponseEntity<PageResponse<CommentaryResponse>> findAllCommentariesByPost(
            @PathVariable("post-id") Long postId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "page", defaultValue = "0", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(commentaryService.findAllCommentariesByPost(postId,page,size,connectedUser));
    }

}
