// src/main/java/com/yourfinancev2/model/User.java
package com.example.yourFinancev2.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usersv2")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private Double monthlySalary;

    private Integer familyMembers;
}
