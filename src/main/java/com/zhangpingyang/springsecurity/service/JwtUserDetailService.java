package com.zhangpingyang.springsecurity.service;

import com.zhangpingyang.springsecurity.bean.JwtUser;
import com.zhangpingyang.springsecurity.dao.UserDao;

import com.zhangpingyang.springsecurity.dao.topic.UserDaoJdbc;
import com.zhangpingyang.springsecurity.entity.User;
import com.zhangpingyang.springsecurity.util.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@CacheConfig
public class JwtUserDetailService implements UserDetailsService {
    @Autowired
    @Qualifier(value = "userDaoJdbc")
    private UserDaoJdbc userDao;

    @Override
    @Cacheable(value = "loadUserByUsername", keyGenerator = "wiselyKeyGenerator")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            System.out.println("db passworredis getd : " + user.getPassword());
            JwtUser jwtUser = JwtUserFactory.create(user);
            return jwtUser;
        }


    }
}
