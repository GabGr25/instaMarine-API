package com.marine.instamarineapi.controller;

import com.marine.instamarinecore.entity.Like;
import com.marine.instamarinecore.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("")
    public ResponseEntity<List<Like>> getAllLikes() {
        List<Like> likes = likeService.findAll();
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Like> getLikeById(@PathVariable UUID id) {
        Like like = likeService.findById(id);
        if (like == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(like);
        }
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<List<Like>> getLikesByPostId(@PathVariable UUID id) {
        List<Like> likes = likeService.getLikesByPostId(id);
        if (likes == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(likes);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Like>> getLikesByUserId(@PathVariable UUID id) {
        List<Like> likes = likeService.getLikesByUserId(id);
        if (likes == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(likes);
        }
    }
    
    @PostMapping("")
    public ResponseEntity<Like> createLike(@RequestBody Like like) throws URISyntaxException {
        Like likeCreated = likeService.save(like);
        if (likeCreated == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(likeCreated.getId())
                    .toUri();

            return ResponseEntity.created(uri).body(likeCreated);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLikeById(@PathVariable UUID id) {
        likeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
