package com.example.yourFinance.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "expenditures")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expenditure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private LocalDateTime date;

    @PrePersist
    public void setDate() {
        if (this.date == null) {
            this.date = LocalDateTime.now();
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public enum Category {
        FOOD,
        TRAVEL,
        UTILITIES,
        ENTERTAINMENT,
        HEALTHCARE,
        EDUCATION,
        GROCERIES,
        TRANSPORTATION,
        HOUSING,
        MISCELLANEOUS
    }

    public void setUser(User user) {
        this.user = user;
    }
}
