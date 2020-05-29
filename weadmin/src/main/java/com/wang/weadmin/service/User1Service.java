package com.wang.weadmin.service;

import com.wang.weadmin.entity.pojo.User1;

/**
 * @description: Spring事务传播机制学习---内部事务
 * @author: WANG Y.G.
 * @time: 2020/04/30 15:29
 * @version: V1.0
 */
public interface User1Service {
    /**
     * 添加用户。
     * 事务传播行为：Propagation.REQUIRED
     * @param user1
     */
    void required(User1 user1);
    /**
     * 添加用户。
     * 事务传播行为：Propagation.REQUIRES_NEW
     * @param user1
     */
    void requiresNew(User1 user1);

    /**
     * 添加用户。
     * 事务传播行为：Propagation.NESTED
     * @param user1
     */
    void nested(User1 user1);

    /**
     * 添加用户。
     * 事务传播行为：Propagation.SUPPORTS
     * @param user1
     */
    void supports(User1 user1);

    /**
     * 添加用户。
     * 事务传播行为：Propagation.NOT_SUPPORTED
     * @param user1
     */
    void notSupported(User1 user1);

    /**
     * 添加用户。
     * 事务传播行为：Propagation.MANDATORY
     */
    void mandatory(User1 user1);

    /**
     * 添加用户。
     * 事务传播行为：Propagation.NEVER
     * @param user1
     */
    void never(User1 user1);
}
