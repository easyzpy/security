package com.zhangpingyang.springsecurity.filter;

import com.zhangpingyang.springsecurity.util.JwtTokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
@WebFilter(urlPatterns = "/", filterName = "jwtFilter")
public class JwtFilter implements Filter {
    private final Logger logger =LoggerFactory.getLogger(JwtFilter.class);
    @Resource(name = "jwtUserDetailService")
    @Qualifier
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtUtil;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        System.out.println("doFilter" + request.getRequestURI());
        HttpSession session = request.getSession(true);
        String token = (String) session.getAttribute("token");
        if (token == null) {

        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
