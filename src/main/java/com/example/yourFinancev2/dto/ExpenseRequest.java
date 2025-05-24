package com.example.yourFinancev2.dto;

import com.example.yourFinancev2.model.Category;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ExpenseRequest {
    private Double amount;
    private String description;
    private Category category;
    private LocalDateTime date; // Optional; if null, use current time
}
