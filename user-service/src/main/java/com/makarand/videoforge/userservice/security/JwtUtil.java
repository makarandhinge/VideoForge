package com.makarand.videoforge.userservice.security;

import com.makarand.videoforge.userservice.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET;

    public JwtUtil(@Value("${jwt.secret}") String SECRET) {
        this.SECRET = SECRET;
    }

    public String generateToken(User user) {
        return Jwts.builder().setSubject(user.getId().toString()).claim("email", user.getEmail())
                .claim("role", user.getRole())
                .claim("name",user.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

}
