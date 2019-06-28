package com.zhangpingyang.springsecurity.filter;

import com.zhangpingyang.springsecurity.bean.JwtUser;
import com.zhangpingyang.springsecurity.util.JwtTokenUtil;
import com.zhangpingyang.springsecurity.util.ObjectMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
@WebFilter(urlPatterns = "/", filterName = "jwtFilter")
public class JwtFilter extends OncePerRequestFilter {
    private final Logger logger =LoggerFactory.getLogger(JwtFilter.class);
    @Resource(name = "jwtUserDetailService")
    @Qualifier
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtUtil;
    private static final String header = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/error")) {
            filterChain.doFilter(request,response);
            return;
        }
        System.out.println(requestURI);
        String token = request.getHeader(JwtFilter.header);
        if (token != null) {
            long start = System.currentTimeMillis();
            String usernameFromToken = jwtUtil.getSubjectFromToken(token);
            long end = System.currentTimeMillis();
            System.out.println("getSubject() cost time " + (end - start) + "ms");
            JwtUser jwtUser = ObjectMapperUtil.getObjectMapper().readValue(usernameFromToken, JwtUser.class);

            System.out.println(jwtUser.getAuthorities());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        }
        filterChain.doFilter(request, response);
    }
}
