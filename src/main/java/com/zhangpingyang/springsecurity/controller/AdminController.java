package com.zhangpingyang.springsecurity.controller;

import com.zhangpingyang.springsecurity.entity.User;
import com.zhangpingyang.springsecurity.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {
    public static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private UserService userService;

    @RequestMapping(value = "list")
    @PreAuthorize("hasRole('USER')")
    public String list(ModelMap model){
        List<User> userList = userService.getUserList(1, 10);
        model.addAttribute("list", userList);

        return "user_list";
    }
    @RequestMapping("detail")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public String detail(@RequestParam(value = "id") String id, ModelMap model){
        if (id == null) {
            return "/error";
        }
        User userById = userService.getUserById(id);
        if (userById == null) {
            return "/error";
        }
        model.addAttribute("user", userById);
        return "user_detail";
    }
}
