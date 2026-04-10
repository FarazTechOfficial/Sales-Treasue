package com.example.distribution_sales_techfira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuReqDTO extends BaseDTO {


    @NotNull
    private String name;
    @NotNull
    private String path;

}
