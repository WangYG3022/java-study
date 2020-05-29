package com.wang.weadmin.service.impl;

import com.wang.weadmin.entity.pojo.User1;
import com.wang.weadmin.mapper.User1Mapper;
import com.wang.weadmin.service.User1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/04/30 15:33
 * @version: V1.0
 */
@Service
public class User1ServiceImpl implements User1Service {
    @Autowired
    private User1Mapper user1Mapper;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void required(User1 user1) {
        user1Mapper.insertSelective(user1);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void requiresNew(User1 user1) {
        user1Mapper.insertSelective(user1);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Override
    public void nested(User1 user1) {
        user1Mapper.insertSelective(user1);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    @Override
    public void supports(User1 user1) {
        user1Mapper.insertSelective(user1);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void notSupported(User1 user1) {
        user1Mapper.insertSelective(user1);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    @Override
    public void mandatory(User1 user1) {
        user1Mapper.insertSelective(user1);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NEVER)
    @Override
    public void never(User1 user1) {
        user1Mapper.insertSelective(user1);
    }
}
