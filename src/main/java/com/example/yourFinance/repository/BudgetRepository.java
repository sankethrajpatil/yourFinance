package com.example.yourFinance.repository;

import com.example.yourFinance.model.Budget;
import com.example.yourFinance.model.User;
import com.example.yourFinance.model.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUser(User user);
    Optional<Budget> findByUserAndCategory(User user, Expenditure.Category category);
}
