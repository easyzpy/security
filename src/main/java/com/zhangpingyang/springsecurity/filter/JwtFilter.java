package com.zhangpingyang.springsecurity.filter;

import com.zhangpingyang.springsecurity.bean.JwtUser;
import com.zhangpingyang.springsecurity.constant.SecurityConstant;
import com.zhangpingyang.springsecurity.util.JwtTokenUtil;
import com.zhangpingyang.springsecurity.util.ObjectMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@Component
@WebFilter(urlPatterns = "/", filterName = "jwtFilter")
public class JwtFilter extends OncePerRequestFilter {
    private final Logger logger =LoggerFactory.getLogger(JwtFilter.class);
    @Resource(name = "jwtUserDetailService")
    @Qualifier
    private UserDetailsService userDetailsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JwtTokenUtil jwtUtil;
    private static final String header = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/error") || requestURI.equals("/favicon.ico")) {
            filterChain.doFilter(request,response);
            return;
        }
        System.out.println(requestURI);
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            filterChain.doFilter(request, response);
            return;
        }
        for (Cookie cookie : cookies) {
            if (cookie != null && cookie.getName().equals(SecurityConstant.token_name) && cookie.getValue() != null) {
                String value = cookie.getValue();
                long start = System.currentTimeMillis();
                String o = (String) redisTemplate.opsForValue().get(value);
                long end = System.currentTimeMillis();
                System.out.println("redis get jwtUser take time :" + (end - start) + "ms");
                if (o == null) {
                    filterChain.doFilter(request,response);
                    return;
                }
                JwtUser jwtUser = ObjectMapperUtil.getObjectMapper().readValue(o, JwtUser.class);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                break;
            }
        }

        filterChain.doFilter(request, response);
    }
}
