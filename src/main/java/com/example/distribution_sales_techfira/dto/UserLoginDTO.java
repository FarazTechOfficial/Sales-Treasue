package com.example.distribution_sales_techfira.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginDTO {
    private Integer id;
    private String name;
    private String email;
    private int status;
    private RoleResDTO role;
}
