package com.example.distribution_sales_techfira.service;

import com.example.distribution_sales_techfira.dto.UserFullResDTO;
import com.example.distribution_sales_techfira.dto.UserResDTO;
import com.example.distribution_sales_techfira.dto.UserReqDTO;
import com.example.distribution_sales_techfira.dto.UserUpdateDTO;
import com.example.distribution_sales_techfira.entity.User;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Map;

public interface UserService {

    UserResDTO save(UserReqDTO userReqDTO);

    UserResDTO findUserByEmail(String email);

    List<UserFullResDTO> findAllUsers();

    void deleteUserById(Integer id);

    UserResDTO updateUserById(Integer id, UserUpdateDTO updateDTO);

    void initUsers();

    public User getUserById(Integer id);
    public void updateStatus(Integer id, int status);

    Map<String, Object> findUsersPaginated(int page, int size);
    public Integer getLoggedInUserId();
}