package com.example.yourFinancev2.service;

import com.example.yourFinancev2.dto.BudgetRequest;
import com.example.yourFinancev2.dto.BudgetResponse;
import com.example.yourFinancev2.model.Budget;
import com.example.yourFinancev2.model.Category;
import com.example.yourFinancev2.model.User;
import com.example.yourFinancev2.repository.BudgetRepository;
import com.example.yourFinancev2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow();
    }

    public BudgetResponse setBudget(BudgetRequest req) {
        User user = getCurrentUser();
        Budget budget = budgetRepository.findByUserAndCategory(user, req.getCategory())
                .orElse(Budget.builder().user(user).category(req.getCategory()).build());
        budget.setAmount(req.getAmount());
        budget = budgetRepository.save(budget);
        return toResponse(budget);
    }

    public List<BudgetResponse> getBudgets() {
        User user = getCurrentUser();
        return budgetRepository.findByUser(user).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public void deleteBudget(Category category) {
        User user = getCurrentUser();
        budgetRepository.findByUserAndCategory(user, category).ifPresent(budgetRepository::delete);
    }

    private BudgetResponse toResponse(Budget budget) {
        BudgetResponse resp = new BudgetResponse();
        resp.setId(budget.getId());
        resp.setCategory(budget.getCategory());
        resp.setAmount(budget.getAmount());
        return resp;
    }
}
