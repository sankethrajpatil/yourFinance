package com.example.yourFinancev2.service;

import com.example.yourFinancev2.dto.ExpenseRequest;
import com.example.yourFinancev2.dto.ExpenseResponse;
import com.example.yourFinancev2.model.Category;
import com.example.yourFinancev2.model.Expense;
import com.example.yourFinancev2.model.User;
import com.example.yourFinancev2.repository.ExpenseRepository;
import com.example.yourFinancev2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow();
    }

    public ExpenseResponse addExpense(ExpenseRequest req) {
        User user = getCurrentUser();
        Expense expense = Expense.builder()
                .user(user)
                .amount(req.getAmount())
                .description(req.getDescription())
                .category(req.getCategory())
                .date(req.getDate() != null ? req.getDate() : LocalDateTime.now())
                .build();
        expense = expenseRepository.save(expense);

        return toResponse(expense);
    }

    public List<ExpenseResponse> getExpenses(LocalDateTime start, LocalDateTime end, Category category, Double min, Double max) {
        User user = getCurrentUser();
        List<Expense> expenses = expenseRepository.findByUser(user);

        return expenses.stream()
                .filter(exp -> (start == null || !exp.getDate().isBefore(start)))
                .filter(exp -> (end == null || !exp.getDate().isAfter(end)))
                .filter(exp -> (category == null || exp.getCategory() == category))
                .filter(exp -> (min == null || exp.getAmount() >= min))
                .filter(exp -> (max == null || exp.getAmount() <= max))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteExpense(Long id) {
        User user = getCurrentUser();
        Expense expense = expenseRepository.findById(id).orElseThrow();
        if (!expense.getUser().getId().equals(user.getId())) throw new RuntimeException("Unauthorized");
        expenseRepository.delete(expense);
    }

    private ExpenseResponse toResponse(Expense expense) {
        ExpenseResponse resp = new ExpenseResponse();
        resp.setId(expense.getId());
        resp.setAmount(expense.getAmount());
        resp.setDescription(expense.getDescription());
        resp.setCategory(expense.getCategory());
        resp.setDate(expense.getDate());
        return resp;
    }
}
