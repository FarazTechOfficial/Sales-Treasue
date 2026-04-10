package com.example.distribution_sales_techfira.mapper;

import com.example.distribution_sales_techfira.dto.UserFullResDTO;
import com.example.distribution_sales_techfira.dto.UserReqDTO;
import com.example.distribution_sales_techfira.dto.UserResDTO;
import com.example.distribution_sales_techfira.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResDTO toDTO(User user) {
        if (user == null) return null;

        UserResDTO dto = new UserResDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setStatus(user.getStatus());
        dto.setRole(user.getRole() != null ? user.getRole().getName() : null);
        return dto;
    }

    public UserFullResDTO toFullDTO(User user) {
        if (user == null) return null;

        UserFullResDTO dto = new UserFullResDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setStatus(user.getStatus());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setCompany(user.getCompany());
        dto.setBranch(user.getBranch());
        dto.setRole(user.getRole());
        return dto;
    }

    public User toEntity(UserReqDTO reqDTO) {
        if (reqDTO == null) return null;

        User user = new User();
        user.setName(reqDTO.getName());
        user.setEmail(reqDTO.getEmail());
        user.setPassword(reqDTO.getPassword());
        user.setRole(reqDTO.getRole());
        return user;
    }

    public List<UserResDTO> toListDTO(List<User> users) {
        return users == null ? null : users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<UserFullResDTO> toListFullDTO(List<User> users) {
        return users == null ? null : users.stream().map(this::toFullDTO).collect(Collectors.toList());
    }
}
