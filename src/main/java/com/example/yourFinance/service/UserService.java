package com.example.yourFinance.service;

import com.example.yourFinance.model.User;
import com.example.yourFinance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Setter
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean registerUser(String username, String password, Double monthlySalary, Integer numDependents) {
        if (userRepository.findByUsername(username).isPresent()) {
            return false;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(User.Role.USER);
        user.setMonthlySalary(monthlySalary);
        user.setNumDependents(numDependents);

        userRepository.save(user);
        return true;
    }

}
