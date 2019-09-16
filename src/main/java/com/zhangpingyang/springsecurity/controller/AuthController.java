package com.zhangpingyang.springsecurity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhangpingyang.springsecurity.StatusException;
import com.zhangpingyang.springsecurity.bean.JwtUser;
import com.zhangpingyang.springsecurity.bean.res.ResponseBean;
import com.zhangpingyang.springsecurity.constant.SecurityConstant;
import com.zhangpingyang.springsecurity.entity.User;
import com.zhangpingyang.springsecurity.enumeration.AuthorityEnum;
import com.zhangpingyang.springsecurity.service.JwtUserDetailService;
import com.zhangpingyang.springsecurity.service.UserService;
import com.zhangpingyang.springsecurity.util.JwtTokenUtil;
import com.zhangpingyang.springsecurity.util.ObjectMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailService jwtUserService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final RedisTemplate redisTemplate;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthenticationManager authenticationManager, JwtUserDetailService jwtUserService, JwtTokenUtil jwtTokenUtil, UserService userService, RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.jwtUserService = jwtUserService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.redisTemplate = redisTemplate;
    }

    @RequestMapping(value = {"index", "/"})
    public String index(HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication.getAuthorities().size()==1 && authentication.getAuthorities().iterator().next().getAuthority().equals(AuthorityEnum.ROLE_TOURIST.name()))) {
            JwtUser user = (JwtUser) authentication.getPrincipal();
            request.setAttribute("user", user);
            request.setAttribute("authorities", user.getAuthorities());
        }
        System.out.println("index");
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "register")
    @ResponseBody
    public ResponseEntity<ResponseBean> register(@RequestBody User user) {
        if (user == null) {
            return ResponseEntity.status(400).build();
        }
        ResponseBean bean = new ResponseBean();
        try {
            User register = userService.register(user);
            return ResponseEntity.ok(bean.success());
        } catch (StatusException e) {
            return ResponseEntity.status(200).body(bean.error(e.getErrorMsg()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(bean.error("error"));
        }
    }

    /**
     * 登录方法
     * @param username
     * @param password
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = {"auth"}, method = RequestMethod.POST)
    public ResponseEntity<ResponseBean> auth(String username, String password, HttpServletResponse response) throws JsonProcessingException {

        if (username == null || password == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        UserDetails userDetails = null;
        try {
            authenticate(username, password);
            userDetails = jwtUserService.loadUserByUsername(username);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new ResponseBean(-1, e.getMessage()));
        }

//        String token = jwtTokenUtil.generateUserToken(userDetails);不在使用token登录 使用uuid 加redis解决
        String uuid = UUID.randomUUID().toString();
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(uuid, ObjectMapperUtil.getObjectMapper().writeValueAsString(userDetails), SecurityConstant.expire_min, TimeUnit.MINUTES);
        Cookie cookie = new Cookie(SecurityConstant.token_name, uuid);
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);

        //登陆方法
        return ResponseEntity.ok(new ResponseBean(0, "登陆成功"));
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
