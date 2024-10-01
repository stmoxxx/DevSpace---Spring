package devspace.devspaceback.controllers;

import devspace.devspaceback.models.PostEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class PostController {

    @GetMapping("/post")
    public ResponseEntity<List<PostEntity>> getPosts(){
        List<PostEntity> posts = new ArrayList<>();

        posts.add(new PostEntity(123, "Malso", "dsadasdasd" ));

        return ResponseEntity.ok(posts);
    }

    @PostMapping("/test-request")
    public ResponseEntity<String> testPostRequest() {
        return ResponseEntity.ok("POST request successful");
    }

}
