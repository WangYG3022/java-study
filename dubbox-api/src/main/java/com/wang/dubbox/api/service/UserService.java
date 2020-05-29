package com.wang.dubbox.api.service;

import com.wang.dubbox.api.pojo.BonusPoint;
import com.wang.dubbox.api.pojo.User;

/**
 * @Description:
 * @Author: WANG Y.G.
 * @Time: 2019/12/20 17:17
 * @Version: V1.0
 */
public interface UserService {
    /**
     * 获取用户姓名、年龄
     * @param id
     * @return
     */
    User getUserById(Integer id);

    /**
     * 根据用户id获取积分信息
     * @param userId
     * @return
     */
    BonusPoint getBonusPointByUserId(Integer userId);
}
