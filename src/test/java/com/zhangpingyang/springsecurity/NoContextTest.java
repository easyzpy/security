package com.zhangpingyang.springsecurity;

import org.hibernate.Session;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * @Author: Zhang Pingyang
 * @Date: 2019/8/23 17:43IllegalArgumentException
 */
public class NoContextTest {
    @Test
    public void test3() {
//        Session.class.getMethod()
        String encode = new BCryptPasswordEncoder().encode("123");
        System.out.println(encode);
    }
    @Test
    public void test2() {
//        char a = '';
        char b = '零';
        int c = 40869;
//        System.out.println((int)a);
        System.out.println((int)b);
        System.out.println((char)c);
        for (int i = 0; i < 10000; i++) {
            System.out.println((char)++c);

        }
    }
    @Test
    public void test1() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\administritor\\Desktop\\x.txt")));

        String s;

        while ((s = in.readLine()) != null) {
            System.out.println(s);
        }
    }
}
