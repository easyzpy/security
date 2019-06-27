package com.zhangpingyang.springsecurity.util;

import com.zhangpingyang.springsecurity.bean.JwtUser;
import com.zhangpingyang.springsecurity.entity.Authority;
import com.zhangpingyang.springsecurity.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
    private static List<GrantedAuthority> mapToGrantedAuthority(List<Authority> authorities){
        List<GrantedAuthority> collect = authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName().name())).collect(Collectors.toList());
        return collect;

    }
}
