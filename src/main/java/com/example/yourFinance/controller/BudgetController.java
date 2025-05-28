package com.example.yourFinance.controller;

import com.example.yourFinance.model.Budget;
import com.example.yourFinance.model.Expenditure;
import com.example.yourFinance.model.User;
import com.example.yourFinance.repository.UserRepository;
import com.example.yourFinance.service.BudgetService;
import com.example.yourFinance.service.ExpenditureService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BudgetController {
    private final ExpenditureService expenditureService;
    private final BudgetService budgetService;
    private final UserRepository userRepository;

    @GetMapping("/budgeting")
    public String showBudgetingPage(Model model) {
        User currentUser = getCurrentUser();
        List<Budget> budgets = budgetService.getBudgetsByUser(currentUser);

        // Get expenditures per category for current month
        Map<Expenditure.Category, Double> spentPerCategory = expenditureService.getSpentThisMonthByCategory(currentUser);

        model.addAttribute("budgets", budgets);
        model.addAttribute("categories", Arrays.asList(Expenditure.Category.values()));
        model.addAttribute("spentPerCategory", spentPerCategory);
        return "budgeting";
    }

    @PostMapping("/budgeting/add")
    public String addBudget(@RequestParam Expenditure.Category category,
                            @RequestParam Double amountPerMonth,
                            Model model) {
        try {
            User currentUser = getCurrentUser();
            Budget budget = budgetService.getBudgetByUserAndCategory(currentUser, category)
                    .orElse(new Budget());
            budget.setUser(currentUser);
            budget.setCategory(category);
            budget.setAmountPerMonth(amountPerMonth);
            budgetService.save(budget);
            return "redirect:/budgeting";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add budget: " + e.getMessage());
            return "budgeting";
        }
    }

    @PostMapping("/budgeting/delete")
    public String deleteBudget(@RequestParam Long id) {
        budgetService.delete(id);
        return "redirect:/budgeting";
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username).orElseThrow();
    }
}
