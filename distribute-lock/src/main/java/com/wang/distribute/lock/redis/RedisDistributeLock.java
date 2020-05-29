package com.wang.distribute.lock.redis;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @description: redis实现分布式锁
 * @author: WANG Y.G.
 * @time: 2020/05/21 15:22
 * @version: V1.0
 */
public class RedisDistributeLock implements Lock {

    private StringRedisTemplate stringRedisTemplate;
    private String lockKey;
    private String lockValue;

    public RedisDistributeLock(StringRedisTemplate stringRedisTemplate, String lockKey) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.lockKey = lockKey;
        lockValue = "";
    }

    @Override
    public void lock() {
         while (!tryLock()) {
             lock();
             try {
                 Thread.sleep(1000L);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        this.lockValue = UUID.randomUUID().toString();
        Boolean tryLock = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, 10, TimeUnit.SECONDS);
        return tryLock;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        if (lockValue.equals(stringRedisTemplate.opsForValue().get(lockKey))) {
            stringRedisTemplate.delete(lockKey);
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
