package com.example.yourFinancev2.dto;

import lombok.Data;

@Data
public class UserProfileResponse {
    private String username;
    private Double monthlySalary;
    private Integer familyMembers;
}
