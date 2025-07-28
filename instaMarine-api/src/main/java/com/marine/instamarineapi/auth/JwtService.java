package com.marine.instamarineapi.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    // Secret de 256 bits minimum pour HS256
    //TODO .env
    private final String SECRET = "mySecretKeyThatIsAtLeast256BitsLongForHS256Algorithm";
    private final int EXPIRATION = 86400; // 24 heures en secondes

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

    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
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