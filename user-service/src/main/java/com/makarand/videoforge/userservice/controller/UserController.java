package com.makarand.videoforge.userservice.controller;

import com.makarand.videoforge.userservice.dto.AuthResponse;
import com.makarand.videoforge.userservice.dto.LoginRequest;
import com.makarand.videoforge.userservice.dto.RegisterRequest;
import com.makarand.videoforge.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        userService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        String token = userService.login(request);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
