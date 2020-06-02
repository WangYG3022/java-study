package com.wang.study.redis;

import com.wang.study.redis.pojo.User;
import com.wang.study.redis.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2019/10/30 15:02
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {
    
    @Autowired
    private UserService userService;
    
    @Test
    public void test() {
        User user = userService.getUserById(2);
        System.out.println(user);
        userService.getUserById(2);
    }
    
}
