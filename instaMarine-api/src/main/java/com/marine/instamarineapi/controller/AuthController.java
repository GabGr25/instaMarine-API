package com.marine.instamarineapi.controller;

import com.marine.instamarineapi.auth.JwtService;
import com.marine.instamarineapi.dto.JwtResponse;
import com.marine.instamarineapi.dto.LoginRequest;
import com.marine.instamarineapi.dto.RegisterRequest;
import com.marine.instamarinecore.entity.User;
import com.marine.instamarinecore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            if (userService.existsByUsername(request.getUsername())) {
                return ResponseEntity.badRequest()
                        .body("Erreur: Username déjà pris!");
            }

            if (userService.existsByEmail(request.getEmail())) {
                return ResponseEntity.badRequest()
                        .body("Erreur: Email déjà utilisé!");
            }

            String hashedPassword = passwordEncoder.encode(request.getPassword());

            User user = new User(
                    request.getUsername(),
                    request.getBio(),
                    request.getEmail(),
                    hashedPassword,
                    null
            );

            User savedUser = userService.save(user);

            String token = jwtService.generateToken(savedUser.getId(), savedUser.getUsername());

            return ResponseEntity.ok(new JwtResponse(token, savedUser.getUsername()));

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Erreur lors de l'inscription: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            User user = userService.findByUsername(request.getUsername());
            if (user == null) {
                return ResponseEntity.badRequest()
                        .body("Utilisateur non trouvé");
            }

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.badRequest()
                        .body("Mot de passe incorrect");
            }

            String token = jwtService.generateToken(user.getId(), user.getUsername());

            return ResponseEntity.ok(new JwtResponse(token, user.getUsername()));

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Erreur lors de la connexion: " + e.getMessage());
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);

                if (jwtService.isTokenValid(token)) {
                    String username = jwtService.getUsernameFromToken(token);
                    return ResponseEntity.ok("Token valide pour: " + username);
                } else {
                    return ResponseEntity.badRequest().body("Token invalide");
                }
            }

            return ResponseEntity.badRequest().body("Header Authorization manquant");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }
}