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

    //Line chart with filter - last 1 month, 3 months, 6 months and 1 year
    @GetMapping("/analytics")
    public String showCategoryAnalytics(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        Map<String, Double> categorySums = expenditureService.getTotalExpenditureByCategory(user);
        List<String> labels = new ArrayList<>(categorySums.keySet());
        List<Double> data = new ArrayList<>(categorySums.values());
        List<Expenditure> expenditures = expenditureService.findByUser(user);
        model.addAttribute("labels", labels);
        model.addAttribute("data", data);

        List<String> dates = expenditures.stream()
                .map(e -> e.getDate().toString()) // format as needed
                .collect(Collectors.toList());

        List<Double> amounts = expenditures.stream()
                .map(Expenditure::getAmount)
                .collect(Collectors.toList());

        model.addAttribute("dates", dates);
        model.addAttribute("amounts", amounts);

        return "analytics";
    }


    //Pie chart for categories with filter - last 1 month, 3 months, 6 months and 1 year

    //Monthly frequency of spending is highest in 1st week, 2nd week or 3rd week or 4th week
}
