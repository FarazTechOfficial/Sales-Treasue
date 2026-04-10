package com.example.distribution_sales_techfira.mapper;


import com.example.distribution_sales_techfira.dto.ForgotUserDTO;
import com.example.distribution_sales_techfira.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ForgotUserMapper {
    ForgotUserMapper INSTANCE = Mappers.getMapper(ForgotUserMapper.class);

    ForgotUserDTO toDTO(User user);
    User toEntity(ForgotUserDTO dto);
}
