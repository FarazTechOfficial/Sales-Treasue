package com.example.distribution_sales_techfira;

import com.example.distribution_sales_techfira.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
public class DistributionSalesTechfiraApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributionSalesTechfiraApplication.class, args);

	}

}
