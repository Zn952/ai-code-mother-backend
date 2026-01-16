package com.zn.aicodemother.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @program: ai-code-mother-backend
 * @description: 用户控制中心
 * @author: Zn
 * @create: 2026-01-16 21:24
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
