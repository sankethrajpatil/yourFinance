package com.example.yourFinancev2.repository;

import com.example.yourFinancev2.model.Budget;
import com.example.yourFinancev2.model.User;
import com.example.yourFinancev2.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByUserAndCategory(User user, Category category);
    List<Budget> findByUser(User user);
}
