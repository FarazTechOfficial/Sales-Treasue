package com.example.distribution_sales_techfira.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleUpdateDTO {
    @NotBlank(message = "Role name is required")
    private String name;
}
