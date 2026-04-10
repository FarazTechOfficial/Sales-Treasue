package com.example.distribution_sales_techfira.mapper;

import com.example.distribution_sales_techfira.dto.ForgotUserDTO;
import com.example.distribution_sales_techfira.entity.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-04T17:43:25+0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
public class ForgotUserMapperImpl implements ForgotUserMapper {

    @Override
    public ForgotUserDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        int id = 0;
        String email = null;
        boolean enabled = false;

        id = user.getId();
        email = user.getEmail();
        enabled = user.isEnabled();

        String username = null;

        ForgotUserDTO forgotUserDTO = new ForgotUserDTO( id, username, email, enabled );

        return forgotUserDTO;
    }

    @Override
    public User toEntity(ForgotUserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setId( dto.getId() );
        user.setEmail( dto.getEmail() );
        user.setEnabled( dto.isEnabled() );

        return user;
    }
}
