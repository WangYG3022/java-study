package com.wang.study.redis.controller;

import com.wang.study.redis.pojo.User;
import com.wang.study.redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/03/03 15:03
 * @version: V1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get/{id}")
    public User get(@PathVariable Integer id) {
        return userService.getUserById(id);
    }
}
