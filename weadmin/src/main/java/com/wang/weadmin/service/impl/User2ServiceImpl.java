package com.wang.weadmin.service.impl;

import com.wang.weadmin.entity.pojo.User2;
import com.wang.weadmin.mapper.User2Mapper;
import com.wang.weadmin.service.User2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/04/30 15:35
 * @version: V1.0
 */
@Service
public class User2ServiceImpl implements User2Service {

    @Autowired
    private User2Mapper user2Mapper;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void required(User2 user2) {
        user2Mapper.insertSelective(user2);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void requiredWithException(User2 user2) {
        user2Mapper.insertSelective(user2);
        throw new RuntimeException("内部方法抛出异常");
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void requiresNew(User2 user2) {
        user2Mapper.insertSelective(user2);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void requiresNewWithException(User2 user2) {
        user2Mapper.insertSelective(user2);
        throw new RuntimeException("内部方法抛出异常");
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Override
    public void nested(User2 user2) {
        user2Mapper.insertSelective(user2);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Override
    public void nestedWithException(User2 user2) {
        user2Mapper.insertSelective(user2);
        throw new RuntimeException("内部方法抛出异常");
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    @Override
    public void supports(User2 user2) {
        user2Mapper.insertSelective(user2);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    @Override
    public void supportsWithException(User2 user2) {
        user2Mapper.insertSelective(user2);
        throw new RuntimeException("内部方法抛出异常");
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void notSupported(User2 user2) {
        user2Mapper.insertSelective(user2);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void notSupportedWithException(User2 user2) {
        user2Mapper.insertSelective(user2);
        throw new RuntimeException("内部方法抛出异常");
    }

    @Override
    public void mandatory(User2 user2) {
        user2Mapper.insertSelective(user2);
    }

    @Override
    public void mandatoryWithException(User2 user2) {
        user2Mapper.insertSelective(user2);
        throw new RuntimeException("内部方法抛出异常");
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NEVER)
    @Override
    public void never(User2 user2) {
        user2Mapper.insertSelective(user2);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NEVER)
    @Override
    public void neverWithException(User2 user2) {
        user2Mapper.insertSelective(user2);
        throw new RuntimeException("内部方法抛出异常");
    }
}
