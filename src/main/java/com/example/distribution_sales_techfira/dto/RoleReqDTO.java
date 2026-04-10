package com.example.distribution_sales_techfira.dto;

import com.example.distribution_sales_techfira.entity.Privilege;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleReqDTO extends BaseDTO{

    @NotNull
    private String name;

    private List<PrivilegeReqDTO> privileges;
    // Note: Using @Getter and @Setter annotations from Lombok
    // instead of @Data to be more explicit
}