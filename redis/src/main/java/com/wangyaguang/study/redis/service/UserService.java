package com.wangyaguang.study.redis.service;

import com.wangyaguang.study.redis.pojo.User;

public interface UserService {
    /**
     * 按ID查找
     * @param id
     * @return
     */
    User getUserById(Integer id);
}
