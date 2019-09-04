package com.zhangpingyang.springsecurity.dao.topic;

import com.zhangpingyang.springsecurity.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Zhang Pingyang
 * @Date: 2019/9/3 14:36
 */
@Repository(value = "userDaoJdbc")
public interface UserDaoJdbc extends CrudRepository<User, String> {
    @Query(value = "select * from jwt_user where username = :username", nativeQuery = true)
    User findByUsername(String username);
    @Query(value = "select * from jwt_user where loginname = :loginname", nativeQuery = true)
    User findByLoginname(String loginname);
//    @Query(value = "from User")
    @Query(value = "select * from jwt_user", nativeQuery = true)
    List<User> getAllUser();
    @Query(value = "select * from jwt_user where phone = :phone", nativeQuery = true)
    User findByPhone(String phone);
    @Query(value = "select * from jwt_user where email = :email", nativeQuery = true)
    User findByEmail(String email);
}
