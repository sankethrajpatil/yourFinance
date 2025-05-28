package com.example.yourFinance.service;

import com.example.yourFinance.model.Budget;
import com.example.yourFinance.model.User;
import com.example.yourFinance.model.Expenditure;
import com.example.yourFinance.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;

    public List<Budget> getBudgetsByUser(User user) {
        return budgetRepository.findByUser(user);
    }

    public Optional<Budget> getBudgetByUserAndCategory(User user, Expenditure.Category category) {
        return budgetRepository.findByUserAndCategory(user, category);
    }

    public Budget save(Budget budget) {
        return budgetRepository.save(budget);
    }

    public void delete(Long id) {
        budgetRepository.deleteById(id);
    }
}
