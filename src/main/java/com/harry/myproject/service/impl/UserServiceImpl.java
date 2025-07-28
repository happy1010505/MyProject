package com.harry.myproject.service.impl;

import com.harry.myproject.dao.UserDao;
import com.harry.myproject.dto.UserRegisterRequest;
import com.harry.myproject.model.User;
import com.harry.myproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
