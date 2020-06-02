package com.wang.study.redis.service.impl;

import com.wang.study.redis.pojo.User;
import com.wang.study.redis.service.UserService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2019/10/30 14:58
 */
@Service
@CacheConfig(cacheManager = "cacheManager", cacheNames = "user")
public class UserServiceImpl implements UserService {
    @Override
    @Cacheable(key = "'user_' + #id")
    public User getUserById(Integer id) {
        System.out.println("===============查MySQL===============");
        return new User(id, "Tom", 20);
    }
}
