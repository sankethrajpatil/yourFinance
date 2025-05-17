package com.example.yourFinance.spec;

import com.example.yourFinance.model.Expenditure;
import com.example.yourFinance.model.User;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenditureSpecification {

    public static Specification<Expenditure> filterBy(
            User user,
            LocalDate fromDate,
            LocalDate toDate,
            Expenditure.Category category,
            Double minAmount,
            Double maxAmount) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Always filter by user
            predicates.add(cb.equal(root.get("user"), user));

            if (fromDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("date"), fromDate));
            }

            if (toDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("date"), toDate));
            }

            // Only filter by category if it's not null or empty
            if (category != null) {
                predicates.add(cb.equal(root.get("category"), category));
            }

            if (minAmount != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("amount"), minAmount));
            }

            if (maxAmount != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("amount"), maxAmount));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
