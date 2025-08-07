package com.marine.instamarineapi.controller;

import com.marine.instamarineapi.auth.JwtService;
import com.marine.instamarineapi.s3.S3Service;
import com.marine.instamarinecore.entity.Post;
import com.marine.instamarinecore.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final JwtService jwtService;
    private final PostService postService;
    private final S3Service s3Service;

    public PostController(PostService postService, JwtService jwtService, S3Service s3Service) {
        this.postService = postService;
        this.jwtService = jwtService;
        this.s3Service = s3Service;
    }

    @GetMapping("")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.findAll();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable UUID id) {
        Post post = postService.findById(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(post);
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable UUID id) {
        List<Post> posts = postService.getPostsByUserId(id);
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(posts);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createPost(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("file") MultipartFile file,
            @RequestParam("caption") String caption,
            @RequestParam("location") String location
    ) {
        try {
            String token = authHeader.substring(7);
            UUID userId = jwtService.getUserIdFromToken(token);

            // Upload S3
            String s3Key = userId + "/" + UUID.randomUUID() + "_" + System.currentTimeMillis() + ".webp";
            String imageUrl = s3Service.uploadFile(s3Key, file.getInputStream(), file.getSize());

            Post post = new Post(userId, imageUrl, caption, location);
            Post createdPost = postService.save(post);

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdPost.getId())
                    .toUri();

            return ResponseEntity.created(uri).body(createdPost);

        } catch (Exception e) {
            System.err.println("❌ Erreur dans createPost: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable UUID id,
            @RequestBody Post post
    ) {
        Post updatedPost = postService.update(id, post);
        if (updatedPost == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updatedPost);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable UUID id) {
        postService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
