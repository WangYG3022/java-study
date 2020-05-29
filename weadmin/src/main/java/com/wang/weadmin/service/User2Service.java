package com.wang.weadmin.service;

import com.wang.weadmin.entity.pojo.User2;

/**
 * @description: Spring事务传播机制学习---内部事务
 * @author: WANG Y.G.
 * @time: 2020/04/30 15:29
 * @version: V1.0
 */
public interface User2Service {
    /**
     * 添加用户。
     * 事务传播行为：Propagation.REQUIRED
     * @param user2
     */
    void required(User2 user2);

    /**
     * 添加用户但抛出异常。
     * 事务传播行为：Propagation.REQUIRED
     * @param user2
     */
    void requiredWithException(User2 user2);
    /**
     * 添加用户。
     * 事务传播行为：Propagation.REQUIRES_NEW
     * @param user2
     */
    void requiresNew(User2 user2);
    /**
     * 添加用户但抛出异常。
     * 事务传播行为：Propagation.REQUIRES_NEW
     * @param user2
     */
    void requiresNewWithException(User2 user2);

    /**
     * 添加用户。
     * 事务传播行为：Propagation.NESTED
     * @param user2
     */
    void nested(User2 user2);

    /**
     * 添加用户但抛出异常。
     * 事务传播行为：Propagation.NESTED
     * @param user2
     */
    void nestedWithException(User2 user2);

    /**
     * 添加用户。
     * 事务传播行为：Propagation.SUPPORTS
     * @param user2
     */
    void supports(User2 user2);

    /**
     * 添加用户但抛出异常。
     * 事务传播行为：Propagation.SUPPORTS
     * @param user2
     */
    void supportsWithException(User2 user2);

    /**
     * 添加用户。
     * 事务传播行为：Propagation.NOT_SUPPORTED
     * @param user2
     */
    void notSupported(User2 user2);

    /**
     * 添加用户但抛出异常。
     * 事务传播行为：Propagation.NOT_SUPPORTED
     * @param user2
     */
    void notSupportedWithException(User2 user2);
    /**
     * 添加用户。
     * 事务传播行为：Propagation.MANDATORY
     */
    void mandatory(User2 user2);
    /**
     * 添加用户但抛出异常。
     * 事务传播行为：Propagation.MANDATORY
     */
    void mandatoryWithException(User2 user2);

    /**
     * 添加用户。
     * 事务传播行为：Propagation.NEVER
     * @param user2
     */
    void never(User2 user2);

    /**
     * 添加用户但抛出异常。
     * 事务传播行为：Propagation.NEVER
     * @param user2
     */
    void neverWithException(User2 user2);
}
