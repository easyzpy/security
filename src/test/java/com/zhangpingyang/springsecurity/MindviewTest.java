package com.zhangpingyang.springsecurity;

import com.zhangpingyang.springsecurity.mindview.PasswordUtils;
import org.junit.Test;

/**
 * @Author: Zhang Pingyang
 * @Date: 2019/9/17 11:18
 */
public class MindviewTest {
    @Test
    public void test3() {

    }
    @Test
    public void test2() {
//        ReflectionUtils.
//        Refle
    }
    @Test
    public void test1() {
        String password = "123";
        boolean b = new PasswordUtils().validatePassword(password);
        System.out.println(b);
    }

}
