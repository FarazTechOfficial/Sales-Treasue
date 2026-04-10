package com.example.distribution_sales_techfira.dto;


import com.example.distribution_sales_techfira.entity.Menu;
import com.example.distribution_sales_techfira.entity.Privilege;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrivilegeReqDTO extends BaseDTO{

    @NotNull
    private String name;
    @NotNull
    private Menu menu;
}
