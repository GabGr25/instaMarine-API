package com.marine.instamarineapi.controller;

import com.marine.instamarinecore.entity.Follow;
import com.marine.instamarinecore.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/follows")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping("")
    public ResponseEntity<List<Follow>> getAllFollows() {
        List<Follow> follows = followService.findAll();
        return ResponseEntity.ok(follows);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Follow> getFollowById(@PathVariable UUID id) {
        Follow follow = followService.findById(id);
        if (follow == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(follow);
        }
    }

    @GetMapping("/follower/{id}")
    public ResponseEntity<List<Follow>> getFollowerById(@PathVariable UUID id) {
        List<Follow> follower = followService.getFollowers(id);
        if (follower == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(follower);
        }
    }

    @GetMapping("/following/{id}")
    public ResponseEntity<List<Follow>> getFollowingById(@PathVariable UUID id) {
        List<Follow> following = followService.getFollowing(id);
        if (following == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(following);
        }
    }

    @PostMapping("")
    public ResponseEntity<Follow> createFollow(@RequestBody Follow follow) throws URISyntaxException {
        Follow createdFollow = followService.save(follow);
        if (createdFollow == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdFollow.getId())
                    .toUri();

            return ResponseEntity.created(uri).body(createdFollow);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFollow(@PathVariable UUID id) {
        followService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
