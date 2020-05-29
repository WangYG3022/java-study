package com.wang.weadmin.service;

import com.wang.weadmin.entity.pojo.User;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/01/27 0:06
 * @version: V1.0
 */
public interface UserService {

    /**
     * 按name查询
     * @param name
     * @return
     */
    User getUserByName(String name);

    /**
     * 按ID查询
     * @param id
     * @return
     */
    User getUserById(Long id);
}
