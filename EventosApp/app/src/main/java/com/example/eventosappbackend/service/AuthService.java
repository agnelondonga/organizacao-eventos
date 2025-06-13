package com.example.eventosappbackend.service;

import com.example.eventosappbackend.dto.LoginRequest;
import com.example.eventosappbackend.dto.RegisterRequest;
import com.example.eventosappbackend.entity.User;
import com.example.eventosappbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User register(RegisterRequest request) {
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }

    public boolean login(LoginRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .map(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .orElse(false);
    }
}
