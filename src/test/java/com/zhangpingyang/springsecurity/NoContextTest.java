package com.zhangpingyang.springsecurity;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author: Zhang Pingyang
 * @Date: 2019/8/23 17:43
 */
public class NoContextTest {
    @Test
    public void test1() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\administritor\\Desktop\\x.txt")));

        String s;

        while ((s = in.readLine()) != null) {
            System.out.println(s);
        }
    }
}
