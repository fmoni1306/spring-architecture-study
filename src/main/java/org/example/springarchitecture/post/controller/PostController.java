package org.example.springarchitecture.post.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.example.springarchitecture.post.controller.port.PostService;
import org.example.springarchitecture.post.controller.response.PostResponse;
import org.example.springarchitecture.post.domain.PostUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시물(posts)")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Builder
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable long id) {
        return ResponseEntity
                .ok()
                .body(PostResponse.from(postService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update(@PathVariable long id, @RequestBody PostUpdate postUpdate) {
        return ResponseEntity
                .ok()
                .body(PostResponse.from(postService.update(id, postUpdate)));
    }

}
