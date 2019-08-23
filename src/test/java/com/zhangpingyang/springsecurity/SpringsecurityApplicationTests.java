package com.zhangpingyang.springsecurity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhangpingyang.springsecurity.entity.Authority;
import com.zhangpingyang.springsecurity.entity.User;
import com.zhangpingyang.springsecurity.service.UserService;
import com.zhangpingyang.springsecurity.util.MyHttpUtil;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringsecurityApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    public void initTopic() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\administritor\\Desktop\\x.txt")));
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        String topic = sb.toString();
//        JSONUtil.

    }
    @Test
    public void testHttpClient() {

        Map<String, String> map = new HashMap<>();
//        page Number 页数
//        tab String 主题分类。目前有 ask share job good
//        limit Number 每一页的主题数量
//        mdrender String 当为 false 时，不渲染。默认为 true，渲染出现的所有 markdown 格式文本。
//        map.put("page", null)
        String s = MyHttpUtil.clientGet("https://cnodejs.org/api/v1/topics", map);
        System.out.println(s);
    }
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
