package com.wang.dubbox.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.wang.dubbox.api.pojo.BonusPoint;
import com.wang.dubbox.api.pojo.User;
import com.wang.dubbox.api.service.UserService;
/**
 * @Description:
 * @Author: WANG Y.G.
 * @Time: 2019/12/20 17:18
 * @version: V1.0
 */
@Service(version = "${user.dubbo.service.version}")
public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(Integer id) {
        return new User(id, "张三", 20);
    }

    @Override
    public BonusPoint getBonusPointByUserId(Integer userId) {
        return new BonusPoint(userId, 100);
    }
}
