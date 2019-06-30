package com.zhangpingyang.springsecurity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhangpingyang.springsecurity.entity.Authority;
import com.zhangpingyang.springsecurity.entity.User;
import com.zhangpingyang.springsecurity.service.UserService;
import com.zhangpingyang.springsecurity.util.ObjectMapperUtil;

import net.minidev.json.JSONUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringsecurityApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void test1() throws JsonProcessingException {
//        Page<User> userList = userService.getUserList(1, 20);
        System.out.println();
//        System.out.println(ObjectMapperUtil.getObjectMapper().writeValueAsString(userList));
    }

    @Test
    public void test2() {
        User user = new User();
        user.setEmail("285442936@qq.com");
        user.setEnable(true);
        user.setFirstName("zhang");
        user.setLastName("pingyang");
        user.setPassword("123");
        user.setUsername("zhangpingyang");
        user.setLastModifyTm(new Date());
        user.setCreateTime(new Date());
        ArrayList<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority(1));
        user.setAuthorities(authorities);
        try {
            User user1 = userService.insertUser(user);
        } catch (DataIntegrityViolationException e) {

            System.out.println("duplicate");
        }
        System.out.println();
    }

    /**
     * 删除user
     */
    @Test
    public void test3() {
        User zhangpingyang = userService.getUserByUsername("zhangpingyang");
        userService.deleteUser(zhangpingyang.getUserId());
    }

}
