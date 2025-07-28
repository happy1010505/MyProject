package com.harry.myproject.dao;

import com.harry.myproject.dto.UserRegisterRequest;
import com.harry.myproject.model.User;

public interface UserDao {
    Integer createUser(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);
}
