package com.zhangpingyang.springsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;

/**
 * @Author: Zhang Pingyang
 * @Date: 2019/9/16 15:50
 */
@Configuration
public class ThymeleafConfig {

    @Resource
    private void configThymeleafVar(ThymeleafViewResolver resolver) {
        if (resolver != null) {
            resolver.addStaticVariable("myTitle", "在云端");
        }
    }
}
