package com.marine.instamarineapi.controller;

import com.marine.instamarineapi.auth.JwtService;
import com.marine.instamarineapi.dto.PostDTO;
import com.marine.instamarineapi.mapper.PostMapper;
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
    private final PostMapper postMapper;

    public PostController(PostService postService, JwtService jwtService, S3Service s3Service, PostMapper postMapper) {
        this.postService = postService;
        this.jwtService = jwtService;
        this.s3Service = s3Service;
        this.postMapper = postMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<PostDTO>> getAllPosts(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        UUID requesterId = jwtService.getUserIdFromToken(token);
        List<Post> posts = postService.findAll();
        List<PostDTO> postsDTO = posts.stream()
                .map(post -> postMapper.toPostDTO(requesterId, post))
                .toList();
        return ResponseEntity.ok(postsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID id
    ) {
        String token = authHeader.substring(7);
        UUID requesterId = jwtService.getUserIdFromToken(token);
        Post post = postService.findById(id);
        PostDTO postDTO = postMapper.toPostDTO(requesterId, post);
        return ResponseEntity.ok(postDTO);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<List<PostDTO>> getPostsByUserId(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID id
    ) {
        String token = authHeader.substring(7);
        UUID requesterId = jwtService.getUserIdFromToken(token);
        List<Post> posts = postService.getPostsByUserId(id);
        List<PostDTO> postsDTO = posts.stream()
                .map(post -> postMapper.toPostDTO(requesterId, post)
                ).toList();
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(postsDTO);
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
            PostDTO createdPostDTO = postMapper.toPostDTO(userId, createdPost);

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdPost.getId())
                    .toUri();

            return ResponseEntity.created(uri).body(createdPostDTO);

        } catch (Exception e) {
            System.err.println("❌ Erreur dans createPost: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID id,
            @RequestBody Post post
    ) {
        String token = authHeader.substring(7);
        UUID userId = jwtService.getUserIdFromToken(token);

        Post updatedPost = postService.update(id, post);
        PostDTO updatedPostDTO = postMapper.toPostDTO(userId, updatedPost);
        return ResponseEntity.ok(updatedPostDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable UUID id) {
        postService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
