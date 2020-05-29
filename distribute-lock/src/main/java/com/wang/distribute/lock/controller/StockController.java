package com.wang.distribute.lock.controller;

import com.wang.distribute.lock.zk.ZookeeperLock;
import org.I0Itec.zkclient.ZkClient;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @description: 使用分布式锁秒杀减库存场景
 * @author: WANG Y.G.
 * @time: 2020/05/21 15:19
 * @version: V1.0
 */
@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private ZkClient zkClient;

    @GetMapping("/redis/{id}")
    public String flashSaleByRedis(@PathVariable String id) {
        String lockKey = "product_" + id;
//        RedisDistributeLock lock = new RedisDistributeLock(stringRedisTemplate, lockKey);
//        boolean flag = lock.tryLock();
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(10, TimeUnit.SECONDS);
//        if (!flag) {
//            // 没有获得锁
//            return "系统繁忙";
//        } else {
            String stock = stringRedisTemplate.opsForValue().get("stock");
            System.out.println("当前库存：" + stock);
            int i = Integer.parseInt(Objects.requireNonNull(stock));
            if (i > 0) {
                stringRedisTemplate.opsForValue().decrement("stock");
                lock.unlock();
                return "抢购成功，剩余库存：" + stringRedisTemplate.opsForValue().get("stock");
            } else {
                lock.unlock();
                return "库存不足";
            }
//            lock.unlock();
//        }
    }

    /*
     *
     */
    @GetMapping("/zk/{id}")
    public String flashSaleByZookeeper(@PathVariable String id) {
        String lockKey = "product_" + id;
        ZookeeperLock lock = new ZookeeperLock(zkClient, lockKey);
        lock.lock();
//        boolean flag = lock.tryLock();
//        if (!flag) {
//            // 没有获得锁
//            return "系统繁忙";
//        } else {
            String stock = stringRedisTemplate.opsForValue().get("stock");
            System.out.println("当前库存：" + stock);
            int i = Integer.parseInt(Objects.requireNonNull(stock));
            if (i > 0) {
                stringRedisTemplate.opsForValue().decrement("stock");
                lock.unlock();
                return "抢购成功，剩余库存：" + stringRedisTemplate.opsForValue().get("stock");
            } else {
                lock.unlock();
                return "库存不足";
            }
//        }
    }
}
