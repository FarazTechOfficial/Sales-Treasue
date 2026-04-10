package com.example.distribution_sales_techfira.controller;


import com.example.distribution_sales_techfira.dto.UserFullResDTO;
import com.example.distribution_sales_techfira.dto.UserReqDTO;
import com.example.distribution_sales_techfira.dto.UserResDTO;
import com.example.distribution_sales_techfira.service.impl.UserServiceImp;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImp userService;

    public AdminController(UserServiceImp userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserResDTO> createUser(@Valid @RequestBody UserReqDTO userReqDTO){
        return ResponseEntity.ok(userService.save(userReqDTO));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserFullResDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

//    @GetMapping("/users/{email}")
//    public ResponseEntity<UserResDTO> getUserByEmail(@PathVariable String email){
//        return ResponseEntity.ok(userService.findUserByEmail(email));
//    }
//
//    @DeleteMapping("/users/{email}")
//    public ResponseEntity<String> deleteUser(@PathVariable String email){
//        return ResponseEntity.ok(userService.deleteUserByEmail(email));
//    }

//    @PutMapping("/users/{email}")
//    public ResponseEntity<UserResDTO> updateUser(@PathVariable String email, @RequestBody @Valid UserReqDTO userReqDTO){
//        return ResponseEntity.ok(userService.updateUser(email,userReqDTO));
//    }

}
