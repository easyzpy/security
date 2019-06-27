package com.zhangpingyang.springsecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @RequestMapping(value = "list")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<Object> list(){
        return ResponseEntity.ok("123");
    }
}
