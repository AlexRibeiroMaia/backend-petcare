package com.petcare.pet_care.infra.security;

import com.petcare.pet_care.domain.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {

    private final String secret;
    private final long expirationMinutes;
    private SecretKey key;

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expiration-minutes:60}") long expirationMinutes) {
        this.secret = secret;
        this.expirationMinutes = expirationMinutes;
    }

    @PostConstruct
    void init() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 bytes");
        }
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user, UUID tutorId, UUID veterinarianId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        claims.put("userId", user.getId().toString());
        claims.put("email", user.getEmail());
        claims.put("name", user.getName());

        if (tutorId != null) {
            claims.put("tutorId", tutorId.toString());
        }
        if (veterinarianId != null) {
            claims.put("veterinarianId", veterinarianId.toString());
        }

        Date now = new Date();
        Date expiry = new Date(now.getTime() + (expirationMinutes * 60_000));

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .addClaims(claims)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public long getExpirationMinutes() {
        return expirationMinutes;
    }
}
