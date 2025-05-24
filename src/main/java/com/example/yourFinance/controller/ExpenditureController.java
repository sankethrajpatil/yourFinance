package com.example.yourFinance.controller;

import com.example.yourFinance.model.Expenditure;
import com.example.yourFinance.model.User;
import com.example.yourFinance.repository.UserRepository;
import com.example.yourFinance.service.ExpenditureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ExpenditureController {

    private final ExpenditureService expenditureService;
    private final UserRepository userRepository;

    @GetMapping("/expenditure")
    @ResponseBody
    public List<String> getExpenditureCategories() {
        return Arrays.stream(Expenditure.Category.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }


    @PostMapping("/expenditure")
    @ResponseBody
    public ResponseEntity<String> submitExpenditure(@RequestBody Expenditure expenditure) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        expenditure.setDate(LocalDateTime.now());
        expenditure.setUser(user);
        expenditureService.save(expenditure);

        return ResponseEntity.ok("Expenditure saved successfully");
    }


    @GetMapping("/api/your-expenditure")
    @ResponseBody
    public List<Expenditure> listExpenditures() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        return expenditureService.findByUser(user);
    }

    @GetMapping("/api/your-expenditure/filter")
    @ResponseBody
    public List<Expenditure> listExpendituresWithParameters(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(required = false) Expenditure.Category category,
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        Specification<Expenditure> spec = com.example.yourFinance.spec.ExpenditureSpecification
                .filterBy(user, fromDate, toDate, category, minAmount, maxAmount);

        return expenditureService.filterExpenditures(spec);
    }
}
