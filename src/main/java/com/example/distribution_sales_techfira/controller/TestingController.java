package com.example.distribution_sales_techfira.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestingController {



    @GetMapping("/test")
    public ResponseEntity<String> welcome(){
        System.out.println("endpoint hit!");
        return ResponseEntity.ok("Test successful!");
    }

}
