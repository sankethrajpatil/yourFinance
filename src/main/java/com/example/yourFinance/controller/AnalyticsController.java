package com.example.yourFinance.controller;

import com.example.yourFinance.model.Expenditure;
import com.example.yourFinance.model.User;
import com.example.yourFinance.repository.UserRepository;
import com.example.yourFinance.service.ExpenditureService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AnalyticsController {

    private final ExpenditureService expenditureService;
    private final UserRepository userRepository;

    @GetMapping("/analytics")
    public String showAnalytics(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        // Category-wise data
        Map<String, Double> categorySums = expenditureService.getTotalExpenditureByCategory(user);
        List<String> labels = new ArrayList<>(categorySums.keySet());
        List<Double> data = new ArrayList<>(categorySums.values());

        model.addAttribute("labels", labels);
        model.addAttribute("data", data);

        // Date-wise data
        List<Expenditure> expenditures = expenditureService.findByUser(user);
        List<String> dates = expenditures.stream()
                .map(e -> e.getDate().toString()) // format as needed
                .collect(Collectors.toList());
        List<Double> amounts = expenditures.stream()
                .map(Expenditure::getAmount)
                .collect(Collectors.toList());

        // Earnings cards
        Map<String, Double> monthlyStats = expenditureService.getMonthlyAnalysis(user);

        model.addAttribute("dates", dates);
        model.addAttribute("amounts", amounts);
        model.addAttribute("monthlyStats", monthlyStats);

        // Recurring Expenses Bar Chart
        model.addAttribute("recurringLabels", expenditureService.getRecurringExpenseLabels());
        model.addAttribute("recurringData", expenditureService.getRecurringExpenseAmounts());

        // Expense Heatmap (Calendar Heatmap)
        model.addAttribute("heatmapMatrix", expenditureService.getHeatmapMatrix());
        model.addAttribute("heatmapDates", expenditureService.getHeatmapDays());


        return "analytics";
    }
}
