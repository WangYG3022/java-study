package com.wang.weadmin.service.impl;

import com.wang.weadmin.entity.pojo.User;
import com.wang.weadmin.mapper.UserMapper;
import com.wang.weadmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/01/27 0:07
 * @version: V1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByName(String name) {
        System.out.println("from DB");
        User user;
        user = (User) redisTemplate.opsForValue().get("user_" + name);
        if (user != null) {
            return user;
        }
        user = new User();
        user.setUserName(name);
        user.setCreateTime(LocalDateTime.now());
        redisTemplate.opsForValue().set("user_" + name, user, Duration.ofMinutes(1L));
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
