package com.example.yourFinance.repository;

import com.example.yourFinance.model.Expenditure;
import com.example.yourFinance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenditureRepository extends JpaRepository<Expenditure, Long>, JpaSpecificationExecutor<Expenditure> {
    List<Expenditure> findByUser(User user); // You can keep this for simpler queries

    @Query("SELECT e.category, SUM(e.amount) FROM Expenditure e WHERE e.user = :user GROUP BY e.category")
    List<Object[]> sumAmountsGroupedByCategory(@Param("user") User user);

    @Query("SELECT e.category, SUM(e.amount) FROM Expenditure e WHERE e.user = :user GROUP BY e.category")
    List<Object[]> dateWiseExpenditure(@Param("user") User user);

    @Query("SELECT e.category, SUM(e.amount) FROM Expenditure e WHERE e.user = :user AND e.date >= :start AND e.date <= :end GROUP BY e.category")
    List<Object[]> sumByCategoryAndUserAndDateBetween(@Param("user") User user, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT SUM(e.amount) FROM Expenditure e WHERE e.user = :user AND e.date >= :start AND e.date <= :end")
    Double sumAmountByUserAndDateBetween(@Param("user") User user, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
