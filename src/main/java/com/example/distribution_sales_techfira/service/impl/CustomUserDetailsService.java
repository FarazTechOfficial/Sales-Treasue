package com.example.distribution_sales_techfira.service.impl;


import com.example.distribution_sales_techfira.entity.User;
import com.example.distribution_sales_techfira.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
   private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User byEmail = userRepository.findByEmail(username);

        return org.springframework.security.core.userdetails.User.withUsername(byEmail.getEmail())
                .password(byEmail.getPassword())
                .roles(byEmail.getRole().getName())
                .build();
    }
}
