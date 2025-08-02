package com.marine.instamarineapi.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final int EXPIRATION = 86400; // 24 heures en secondes
    // Secret de 256 bits minimum pour HS256
    private final String SECRET;

    public JwtService(@Value("${SECRET_KEY}") String secret) {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(secret);
            if (keyBytes.length < 32) {
                throw new IllegalArgumentException("SECRET_KEY doit encoder au moins 256 bits");
            }
            this.SECRET = secret;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("SECRET_KEY doit être en Base64 valide", e);
        }
    }

    public String generateToken(UUID userId, String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION * 1000L);

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("username", username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public UUID getUserIdFromToken(String token) {
        String userIdStr = getClaims(token).getSubject();
        return UUID.fromString(userIdStr);
    }

    public String getUsernameFromToken(String token) {
        return getClaims(token).get("username", String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.err.println("Token expiré: " + e.getMessage());
            return false;
        } catch (JwtException e) {
            System.err.println("Token invalide: " + e.getMessage());
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}