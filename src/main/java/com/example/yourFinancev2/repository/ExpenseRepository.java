package com.example.yourFinancev2.repository;

import com.example.yourFinancev2.model.Expense;
import com.example.yourFinancev2.model.User;
import com.example.yourFinancev2.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
    List<Expense> findByUserAndCategory(User user, Category category);
    List<Expense> findByUserAndDateBetween(User user, LocalDateTime start, LocalDateTime end);
    List<Expense> findByUserAndAmountBetween(User user, Double min, Double max);
    // Add more custom queries as needed for filtering
}
