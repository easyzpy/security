package com.zhangpingyang.springsecurity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhangpingyang.springsecurity.constant.SecurityConstant;
import com.zhangpingyang.springsecurity.service.JwtUserDetailService;
import com.zhangpingyang.springsecurity.util.JwtTokenUtil;
import com.zhangpingyang.springsecurity.util.ObjectMapperUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUserDetailService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping(value = "index")
    public String index(){
        return "index";
    }

    /**
     * 登录方法
     * @param username
     * @param password
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = {"auth"}, method = RequestMethod.POST)
    public ResponseEntity auth(String username, String password, HttpServletResponse response) throws JsonProcessingException {

        if (username == null || password == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        authenticate(username, password);
        UserDetails userDetails = userService.loadUserByUsername(username);

//        String token = jwtTokenUtil.generateUserToken(userDetails);不在使用token登录 使用uuid 加redis解决
        String uuid = UUID.randomUUID().toString();
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(uuid, ObjectMapperUtil.getObjectMapper().writeValueAsString(userDetails), 10, TimeUnit.MINUTES);
        Cookie cookie = new Cookie(SecurityConstant.token_name, uuid);
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);

        //登陆方法
        return ResponseEntity.ok("ok");
    }

    private void authenticate(String username, String password) throws AuthenticationException{
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new DisabledException("User is disabled!", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Bad credential", e);
        }


    }

}
