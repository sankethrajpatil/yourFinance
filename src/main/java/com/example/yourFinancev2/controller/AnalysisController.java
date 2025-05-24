package com.example.yourFinancev2.controller;

import com.example.yourFinancev2.model.Category;
import com.example.yourFinancev2.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/analysis")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;

    @GetMapping("/category-totals")
    public Map<Category, Double> getTotalsPerCategory(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return analysisService.getTotalPerCategory(start, end);
    }

    @GetMapping("/monthly-totals")
    public Map<String, Double> getMonthlyTotals(
            @RequestParam(defaultValue = "6") int monthsBack
    ) {
        return analysisService.getMonthlyTotals(monthsBack);
    }
}
