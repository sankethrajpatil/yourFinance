package com.example.yourFinance.service;

import com.example.yourFinance.model.Expenditure;
import com.example.yourFinance.model.User;
import com.example.yourFinance.repository.ExpenditureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExpenditureService {

    private final ExpenditureRepository repo;
    private final ExpenditureRepository expenditureRepository;


    public Expenditure save(Expenditure e) {
        return repo.save(e);
    }

    public List<Expenditure> findByUser(User user) {
        return repo.findByUser(user);
    }

    public List<Expenditure> filterExpenditures(Specification<Expenditure> spec) {
        return expenditureRepository.findAll(spec);
    }

    public Map<String, Double> getTotalExpenditureByCategory(User user) {
        List<Object[]> results = expenditureRepository.sumAmountsGroupedByCategory(user);
        Map<String, Double> categoryTotals = new HashMap<>();

        for (Object[] row : results) {
            String category = row[0].toString();
            Double total = (Double) row[1];
            categoryTotals.put(category, total);
        }

        return categoryTotals;
    }

}
