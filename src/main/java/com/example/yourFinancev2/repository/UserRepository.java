// src/main/java/com/yourfinancev2/repository/UserRepository.java
package com.example.yourFinancev2.repository;

import com.example.yourFinancev2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
