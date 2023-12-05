package com.rocky.controller;

import com.rocky.model.UserLoginInfo;
import com.rocky.model.UserRegiserInfo;
import com.rocky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Integer addUser(@RequestBody UserRegiserInfo userRegiserInfo){

        return userService.addUser(userRegiserInfo);
    }

    @PostMapping("/login")
    public String addUser(@RequestBody UserLoginInfo loginInfo){

        return userService.login(loginInfo);
    }
}
