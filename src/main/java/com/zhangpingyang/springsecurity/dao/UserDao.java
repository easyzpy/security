package com.zhangpingyang.springsecurity.dao;

import com.zhangpingyang.springsecurity.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
