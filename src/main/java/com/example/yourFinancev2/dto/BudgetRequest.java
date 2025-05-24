package com.example.yourFinancev2.dto;

import com.example.yourFinancev2.model.Category;
import lombok.Data;

@Data
public class BudgetRequest {
    private Category category;
    private Double amount;
}
