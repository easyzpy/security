package com.zhangpingyang.springsecurity.util;

import com.zhangpingyang.springsecurity.bean.JwtUser;
import com.zhangpingyang.springsecurity.bean.MySimpleGrantedAuthority;
import com.zhangpingyang.springsecurity.entity.Authority;
import com.zhangpingyang.springsecurity.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        JwtUser jwtUser = new JwtUser(user.getUserId()
                , user.getUsername()
                , user.getFirstName()
                , user.getLastName()
                , user.getPassword()
                , user.getEmail()
                , mapToGrantedAuthority(user.getAuthorities())
                , user.getEnable()
                , user.getLastModifyTm());
        return jwtUser;
    }
    private static List<MySimpleGrantedAuthority> mapToGrantedAuthority(List<Authority> authorities){
        List<MySimpleGrantedAuthority> collect = authorities.stream().map(authority -> new MySimpleGrantedAuthority(authority.getAuthority().name())).collect(Collectors.toList());
        return collect;

    }
}
