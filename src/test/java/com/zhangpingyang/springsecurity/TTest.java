package com.zhangpingyang.springsecurity;

import org.junit.Test;

public class TTest {
    @Test
    public void test1(){
        String s = null;
        if (1 == 2){
            s = "";
        }
        try {
            byte[] bytes = s.getBytes();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
