package com.zhangpingyang.springsecurity.service;

import com.zhangpingyang.springsecurity.dao.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailService implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        User user =
        return null;
    }
}