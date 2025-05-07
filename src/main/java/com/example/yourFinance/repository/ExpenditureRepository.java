package com.example.yourFinance.repository;

import com.example.yourFinance.model.Expenditure;
import com.example.yourFinance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenditureRepository extends JpaRepository<Expenditure, Long> {
    List<Expenditure> findByUser(User user);
}
