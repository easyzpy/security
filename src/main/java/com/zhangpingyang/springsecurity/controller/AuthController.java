package com.zhangpingyang.springsecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@RequestMapping(value = ")
@Controller
public class AuthController {

    @RequestMapping(value = "auth", method = RequestMethod.POST)
    public ResponseEntity<String> auth(){
        return ResponseEntity.ok("okoki");
    }
}
