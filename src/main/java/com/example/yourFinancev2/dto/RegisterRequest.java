// src/main/java/com/yourfinancev2/dto/RegisterRequest.java
package com.example.yourFinancev2.dto;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private Double monthlySalary;
    private Integer familyMembers;
}
