package com.marine.instamarineapi.controller;

import com.marine.instamarineapi.auth.JwtService;
import com.marine.instamarineapi.dto.UserDTO;
import com.marine.instamarineapi.mapper.UserMapper;
import com.marine.instamarinecore.entity.User;
import com.marine.instamarinecore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    public UserController(UserService userService, UserMapper userMapper, JwtService jwtService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.findAll();
        List<UserDTO> usersDTO = users.stream().map(userMapper::toUsersDTO).toList();
        return ResponseEntity.ok(usersDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID id
    ) {
        String token = authHeader.substring(7);
        UUID requesterId = jwtService.getUserIdFromToken(token);
        User user = userService.findById(id);
        UserDTO userDTO = userMapper.toUserDTO(requesterId, user);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID id,
            @RequestBody User user
    ) {
        String token = authHeader.substring(7);
        UUID requesterId = jwtService.getUserIdFromToken(token);
        User updatedUser = userService.update(id, user);
        UserDTO updatedUserDTO = userMapper.toUserDTO(requesterId, updatedUser);
        return ResponseEntity.ok(updatedUserDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
