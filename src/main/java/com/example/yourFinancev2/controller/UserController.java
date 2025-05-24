package com.example.yourFinancev2.controller;

import com.example.yourFinancev2.dto.UserProfileResponse;
import com.example.yourFinancev2.model.User;
import com.example.yourFinancev2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public UserProfileResponse getProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        UserProfileResponse response = new UserProfileResponse();
        response.setUsername(user.getUsername());
        response.setMonthlySalary(user.getMonthlySalary());
        response.setFamilyMembers(user.getFamilyMembers());
        return response;
    }
}
