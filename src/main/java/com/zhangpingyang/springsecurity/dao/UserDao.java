package com.zhangpingyang.springsecurity.dao;

import com.zhangpingyang.springsecurity.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query(value = "from User")
    List<User> getAllUser();
}
