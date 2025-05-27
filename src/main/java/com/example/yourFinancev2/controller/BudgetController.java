package com.example.yourFinancev2.controller;

import com.example.yourFinancev2.dto.BudgetRequest;
import com.example.yourFinancev2.dto.BudgetResponse;
import com.example.yourFinancev2.model.Category;
import com.example.yourFinancev2.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @PostMapping
    public BudgetResponse setBudget(@RequestBody BudgetRequest request) {
        return budgetService.setBudget(request);
    }

    @GetMapping
    public List<BudgetResponse> getBudgets() {
        return budgetService.getBudgets();
    }

    @DeleteMapping("/{category}")
    public void deleteBudget(@PathVariable Category category) {
        budgetService.deleteBudget(category);
    }
}
