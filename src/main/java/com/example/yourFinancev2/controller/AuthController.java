package com.example.yourFinancev2.controller;

import com.example.yourFinancev2.dto.RegisterRequest;
import com.example.yourFinancev2.dto.LoginRequest;
import com.example.yourFinancev2.dto.AuthResponse;
import com.example.yourFinancev2.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
