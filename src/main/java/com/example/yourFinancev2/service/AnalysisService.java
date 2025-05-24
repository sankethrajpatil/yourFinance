package com.example.yourFinancev2.service;

import com.example.yourFinancev2.model.Category;
import com.example.yourFinancev2.model.Expense;
import com.example.yourFinancev2.model.User;
import com.example.yourFinancev2.repository.ExpenseRepository;
import com.example.yourFinancev2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;

@Service
public class AnalysisService {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow();
    }

    public Map<Category, Double> getTotalPerCategory(LocalDateTime start, LocalDateTime end) {
        User user = getCurrentUser();
        List<Expense> expenses = expenseRepository.findByUser(user);
        Map<Category, Double> totals = new HashMap<>();
        for (Category cat : Category.values()) {
            double sum = expenses.stream()
                    .filter(e -> e.getCategory() == cat)
                    .filter(e -> (start == null || !e.getDate().isBefore(start)))
                    .filter(e -> (end == null || !e.getDate().isAfter(end)))
                    .mapToDouble(Expense::getAmount).sum();
            totals.put(cat, sum);
        }
        return totals;
    }

    public Map<String, Double> getMonthlyTotals(int monthsBack) {
        User user = getCurrentUser();
        List<Expense> expenses = expenseRepository.findByUser(user);
        Map<String, Double> monthlyTotals = new LinkedHashMap<>();
        YearMonth now = YearMonth.now();

        for (int i = 0; i < monthsBack; i++) {
            YearMonth ym = now.minusMonths(i);
            double sum = expenses.stream()
                    .filter(e -> YearMonth.from(e.getDate()).equals(ym))
                    .mapToDouble(Expense::getAmount)
                    .sum();
            monthlyTotals.put(ym.toString(), sum);
        }
        return monthlyTotals;
    }
}
