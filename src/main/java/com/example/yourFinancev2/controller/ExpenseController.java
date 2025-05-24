package com.example.yourFinancev2.controller;

import com.example.yourFinancev2.dto.ExpenseRequest;
import com.example.yourFinancev2.dto.ExpenseResponse;
import com.example.yourFinancev2.model.Category;
import com.example.yourFinancev2.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ExpenseResponse addExpense(@RequestBody ExpenseRequest request) {
        return expenseService.addExpense(request);
    }

    @GetMapping
    public List<ExpenseResponse> getExpenses(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max
    ) {
        return expenseService.getExpenses(start, end, category, min, max);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }
}
