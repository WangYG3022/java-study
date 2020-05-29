package com.wang.dubbox.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.wang.dubbox.api.pojo.BonusPoint;
import com.wang.dubbox.api.pojo.User;
import com.wang.dubbox.api.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: WANG Y.G.
 * @Time: 2019/12/21 15:46
 * @version: V1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(version = "${user.dubbo.service.version}")
    private UserService userService;

    @GetMapping("/find/{id}")
    public JSONObject getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", user.getId());
        jsonObject.put("username", user.getUsername());
        jsonObject.put("age", user.getAge());
        return jsonObject;
    }

    @GetMapping("/point/{userId}")
    public JSONObject getBonusPointByUserId(@PathVariable Integer userId) {
        BonusPoint bonusPoint = userService.getBonusPointByUserId(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", bonusPoint.getId());
        jsonObject.put("points", bonusPoint.getPoints());
        return jsonObject;
    }
}
