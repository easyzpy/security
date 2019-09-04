package com.zhangpingyang.springsecurity.dao;

import com.zhangpingyang.springsecurity.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDaoJpa")
public interface UserDao extends JpaRepository<User, String> {
    User findByUsername(String username);
    User findByLoginname(String loginname);
    @Query(value = "from User")
    List<User> getAllUser();

    User findByPhone(String phone);

    User findByEmail(String email);
}
