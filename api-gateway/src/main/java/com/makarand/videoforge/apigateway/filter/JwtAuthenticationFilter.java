package com.makarand.videoforge.apigateway.filter;

import com.makarand.videoforge.apigateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {


        String path = exchange.getRequest().getURI().getPath();
        log.debug("Processing request for path: {}", path);

        if (path.startsWith("/api/v1/auth/")) {
            log.debug("Skipping authentication for auth path: {}", path);
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        log.debug("Authorization header present: {}", authHeader != null);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Missing or invalid Authorization header for path: {}", path);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
        log.debug("Extracted token (first 20 chars): {}...", token.length() > 20 ? token.substring(0, 20) : token);

        try {
            Claims claims = jwtUtil.validateToken(token);
            log.debug("Token validated successfully. Subject: {}, Role: {}", claims.getSubject(), claims.get("role"));

            // Create new headers with additional user info
            HttpHeaders newHeaders = new HttpHeaders();
            newHeaders.putAll(exchange.getRequest().getHeaders());
            newHeaders.add("X-USER-ID", claims.getSubject());
            newHeaders.add("X-USER-ROLE", String.valueOf(claims.get("role")));

            // Create decorated request with new headers
            ServerHttpRequest decoratedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public HttpHeaders getHeaders() {
                    return newHeaders;
                }
            };

            return chain.filter(exchange.mutate().request(decoratedRequest).build());

        } catch (Exception e) {
            log.error("Token validation failed for path: {}. Error: {}", path, e.getMessage(), e);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -100; // Run before other filters
    }
}