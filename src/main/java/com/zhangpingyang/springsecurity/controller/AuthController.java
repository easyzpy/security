package com.zhangpingyang.springsecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//@RequestMapping(value = ")
@Controller
public class AuthController {

    @RequestMapping(value = "auth", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> auth(){
        return ResponseEntity.ok("okoki");
    }
    @RequestMapping(value = "/auth/xxx")
    public String xxx(){
        return "xxx";
    }
}
