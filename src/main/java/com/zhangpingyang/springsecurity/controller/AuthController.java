package com.zhangpingyang.springsecurity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhangpingyang.springsecurity.service.JwtUserDetailService;
import com.zhangpingyang.springsecurity.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUserDetailService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
//    @Autowired
//    private PasswordEncoder encoder;
    @RequestMapping(value = "auth", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity auth(String username, String password) throws JsonProcessingException {

        if (username == null || password == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        authenticate(username, password);
        UserDetails userDetails = userService.loadUserByUsername(username);
//        String token = jwtTokenUtil.generateUsernameToken(userDetails);
        String token = jwtTokenUtil.generateUserToken(userDetails);
        System.out.println(token);
        //登陆方法
        return ResponseEntity.ok(token);
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
