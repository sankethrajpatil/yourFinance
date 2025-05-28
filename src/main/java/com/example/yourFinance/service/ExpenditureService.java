package com.example.yourFinance.service;

import com.example.yourFinance.model.Expenditure;
import com.example.yourFinance.model.User;
import com.example.yourFinance.repository.ExpenditureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

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

    // In ExpenditureService.java
    public Map<Expenditure.Category, Double> getSpentThisMonthByCategory(User user) {
        LocalDate startLocalDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endLocalDate = startLocalDate.plusMonths(1).minusDays(1);

        LocalDateTime start = startLocalDate.atStartOfDay();
        LocalDateTime end = endLocalDate.atTime(23, 59, 59);

        List<Object[]> results = expenditureRepository.sumByCategoryAndUserAndDateBetween(user, start, end);
        Map<Expenditure.Category, Double> map = new HashMap<>();
        for (Object[] row : results) {
            Expenditure.Category cat = (Expenditure.Category) row[0];
            Double sum = (Double) row[1];
            map.put(cat, sum != null ? sum : 0.0);
        }
        return map;
    }

    public Map<String, Double> getMonthlyAnalysis(User user) {
        Map<String, Double> result = new HashMap<>();
        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = start.plusMonths(1).minusDays(1);

        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(23, 59, 59);

        // Use startDateTime and endDateTime for your repository query
        Double totalExpenditure = expenditureRepository.sumAmountByUserAndDateBetween(user, startDateTime, endDateTime);
        if (totalExpenditure == null) totalExpenditure = 0.0;

        double salary = user.getMonthlySalary(); // Assumes User has getSalary()

        result.put("salary", salary);
        result.put("expenditure", totalExpenditure);
        result.put("savings", salary - totalExpenditure);

        return result;
    }

    // 3. Recurring Expenses Bar Chart (by description)
    public List<String> getRecurringExpenseLabels() {
        List<Expenditure> all = expenditureRepository.findAll();
        Map<String, Long> counts = all.stream()
                .collect(groupingBy(
                        e -> e.getDescription().toLowerCase().trim(),
                        counting()
                ));
        // Only those that appear more than once
        return counts.entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(toList());
    }

    public List<Double> getRecurringExpenseAmounts() {
        List<Expenditure> all = expenditureRepository.findAll();
        Map<String, Double> sums = all.stream()
                .collect(groupingBy(
                        e -> e.getDescription().toLowerCase().trim(),
                        summingDouble(Expenditure::getAmount)
                ));
        List<String> labels = getRecurringExpenseLabels();
        List<Double> data = new ArrayList<>();
        for (String label : labels) {
            data.add(sums.getOrDefault(label, 0.0));
        }
        return data;
    }

    public List<Map<String, Object>> getHeatmapMatrix() {
        List<Expenditure> all = expenditureRepository.findAll();

        // Define the range in LocalDateTime
        LocalDate start = LocalDate.now().withDayOfMonth(1).minusMonths(1);
        LocalDate end = LocalDate.now().withDayOfMonth(1).plusMonths(1).minusDays(1);

        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(23, 59, 59);

        // Filter with LocalDateTime, group by LocalDate (day)
        Map<LocalDate, Double> daily = all.stream()
                .filter(e -> !e.getDate().isBefore(startDateTime) && !e.getDate().isAfter(endDateTime))
                .collect(Collectors.groupingBy(
                        e -> e.getDate().toLocalDate(), // group by just the date part
                        Collectors.summingDouble(Expenditure::getAmount)
                ));

        List<Map<String, Object>> matrix = new ArrayList<>();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            int weekOfMonth = date.get(weekFields.weekOfMonth());
            int dayOfWeek = date.getDayOfWeek().getValue() - 1; // 0 = Monday
            double value = daily.getOrDefault(date, 0.0);
            Map<String, Object> cell = new HashMap<>();
            cell.put("x", dayOfWeek);
            cell.put("y", weekOfMonth - 1);
            cell.put("v", value);
            matrix.add(cell);
        }
        return matrix;
    }

    public List<String> getHeatmapDays() {
        return Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
    }


}
