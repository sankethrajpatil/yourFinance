package com.example.yourFinance.model;

import jakarta.persistence.*;

@Entity
@Table(name = "budgets")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Expenditure.Category category;

    private Double amountPerMonth;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Expenditure.Category getCategory() { return category; }
    public void setCategory(Expenditure.Category category) { this.category = category; }

    public Double getAmountPerMonth() { return amountPerMonth; }
    public void setAmountPerMonth(Double amountPerMonth) { this.amountPerMonth = amountPerMonth; }
}
