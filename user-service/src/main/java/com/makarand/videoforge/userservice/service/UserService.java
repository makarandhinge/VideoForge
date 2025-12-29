package com.makarand.videoforge.userservice.service;

import com.makarand.videoforge.userservice.dto.LoginRequest;
import com.makarand.videoforge.userservice.dto.RegisterRequest;
import com.makarand.videoforge.userservice.model.User;
import com.makarand.videoforge.userservice.repository.UserRepository;
import com.makarand.videoforge.userservice.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public void register(RegisterRequest request) {

         if(userRepository.existsByEmail(request.getEmail())){
             throw new RuntimeException("Email already registered");
        }

         User user = new User();
         String fullName = request.getFirstName() + " " + request.getLastName();
         user.setName(fullName);
         user.setEmail(request.getEmail());
         user.setPassword(passwordEncoder.encode(request.getPassword()));

         userRepository.save(user);
    }

    public String login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user);

    }
}
