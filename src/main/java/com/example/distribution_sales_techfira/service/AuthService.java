package com.example.distribution_sales_techfira.service;

import com.example.distribution_sales_techfira.dto.LoginReqDTO;
import com.example.distribution_sales_techfira.dto.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO verify(LoginReqDTO loginReqDTO);
}
