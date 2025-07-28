package com.harry.myproject.service;


import com.harry.myproject.dto.UserRegisterRequest;
import com.harry.myproject.model.User;

public interface UserService {
    Integer register(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);
}
