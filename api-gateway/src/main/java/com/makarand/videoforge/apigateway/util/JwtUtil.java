package com.makarand.videoforge.apigateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
@Component
public class JwtUtil {

    private final String SECRET = "8Fq9$KpL3@xZ2A!9mN6R7T#VwYQ5EoJHcU";

    public Claims validateToken(String token){
        return Jwts.parserBuilder().setSigningKey(
                        Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8))
                )
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
