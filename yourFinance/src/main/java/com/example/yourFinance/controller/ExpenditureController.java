package com.example.yourFinance.controller;

import com.example.yourFinance.model.Expenditure;
import com.example.yourFinance.model.User;
import com.example.yourFinance.repository.UserRepository;
import com.example.yourFinance.service.ExpenditureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ExpenditureController {

    private final ExpenditureService expenditureService;
    private final UserRepository userRepository;

    @GetMapping("/expenditure")
    public String showForm(Model model) {
        model.addAttribute("expenditure", new Expenditure());
        model.addAttribute("categories", Arrays.asList(Expenditure.Category.values()));
        return "expenditure";
    }

    @PostMapping("/expenditure")
    public String submitExpenditure(@ModelAttribute Expenditure expenditure) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow();

        expenditure.setDate(LocalDateTime.from(LocalDateTime.now()));
        expenditure.setUser(user);
        expenditureService.save(expenditure);
        return "redirect:/your-expenditure";
    }

    @GetMapping("/your-expenditure")
    public String listExpenditures(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow();
        model.addAttribute("items", expenditureService.findByUser(user));
        return "your-expenditure";
    }

    @GetMapping("/your-expenditure/filter")
    public String listExpendituresWithParameters(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(required = false) Expenditure.Category category,
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount,
            Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        Specification<Expenditure> spec = com.example.yourFinance.spec.ExpenditureSpecification.filterBy(user, fromDate, toDate, category, minAmount, maxAmount);
        List<Expenditure> items = expenditureService.filterExpenditures(spec);
        model.addAttribute("items", items);
        return "your-expenditure";
    }
}
