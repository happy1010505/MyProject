package com.harry.myproject.dao.impl;

import com.harry.myproject.dao.UserDao;
import com.harry.myproject.dto.UserRegisterRequest;
import com.harry.myproject.model.User;
import com.harry.myproject.rowMapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql = "INSERT INTO user (email,password,created_date,last_modified_date) VALUES (:email,:password,:createdDate,:lastModifiedDate)";
        Map<String, Object> map = new HashMap<>();
        map.put("email", userRegisterRequest.getEmail());
        map.put("password", userRegisterRequest.getPassword());
        map.put("createdDate",new Date());
        map.put("lastModifiedDate",new Date());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public User getUserById(Integer userId) {
        String sql = "SELECT user_id,email,password,created_date,last_modified_date FROM user WHERE user_id = :userId";
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        List<User> userList = namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(map), new UserRowMapper());
        if(userList.isEmpty()){
            return null;
        }
        return userList.get(0);
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT user_id,email,password,created_date,last_modified_date FROM user WHERE email = :email";
        Map<String,Object> map = new HashMap<>();
        map.put("email",email);
        List<User> userList = namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(map), new UserRowMapper());
        if(userList.isEmpty()){
            return null;
        }
        return userList.get(0);
    }
}
