package com.example.distribution_sales_techfira.dto;

import com.example.distribution_sales_techfira.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponseDTO {
    private String token;
    private UserLoginDTO user; // or UserDTO if you want to return a cleaned version

    // Constructors, Getters, Setters
}
