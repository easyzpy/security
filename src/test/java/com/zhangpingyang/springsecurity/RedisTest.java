package com.zhangpingyang.springsecurity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhangpingyang.springsecurity.bean.JwtUser;
import com.zhangpingyang.springsecurity.util.ObjectMapperUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void test1() throws JsonProcessingException {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        ObjectMapper om = ObjectMapperUtil.getObjectMapper();
        JwtUser jwtUser = new JwtUser();
        jwtUser.setLastname("lastName");
        jwtUser.setUsername("zhangpingyang");
        jwtUser.setPassword("password");
        valueOperations.set(UUID.randomUUID().toString(),  om.writeValueAsString(jwtUser), 60, TimeUnit.SECONDS);
    }
}
