package com.example.eventosappbackend.controller;

import com.example.eventosappbackend.dto.LoginRequest;
import com.example.eventosappbackend.dto.RegisterRequest;
import com.example.eventosappbackend.entity.User;
import com.example.eventosappbackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        boolean authenticated = authService.login(request);
        return authenticated ? "Login successful" : "Invalid credentials";
    }
}
