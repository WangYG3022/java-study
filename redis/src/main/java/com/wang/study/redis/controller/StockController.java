package com.wang.study.redis.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description: redis实现分布式锁，减库存
 * @author: WANG Y.G.
 * @time: 2019/10/30 14:58
 */
@RestController
@RequestMapping("product")
public class StockController {
    
    private static final Log log = LogFactory.getLog(StockController.class);
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private Redisson redisson;
    
    @RequestMapping("deduct_stock/{productId}/{quantity}")
    public String deductStock(@PathVariable("productId") Integer productId, @PathVariable("quantity") Integer quantity) {
        final String lockKey = productId.toString();
        final String uuid = UUID.randomUUID().toString();
        // redisson获取分布式锁
        RLock lock = redisson.getLock(lockKey);
        try {
            lock.lock(10, TimeUnit.SECONDS);
            // 获得锁，并设置失效时间，防止服务挂机无法释放锁
            //Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, uuid);
            //stringRedisTemplate.expire(lockKey, 10, TimeUnit.SECONDS);
            /*
            // 上面两行代码非原子性，合并成下面一行
            Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, uuid, 10, TimeUnit.SECONDS);
            if (!flag) { // 没获得锁
                return "没获得锁，友好提示。";
            }
            */
            // 获得锁，处理减库存业务
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - quantity;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("商品" + productId + "扣减库存成功，剩余：" + realStock);
            } else {
                System.out.println("库存不足！！！");
            }
        } finally {
            /*
            if (uuid.equals(stringRedisTemplate.opsForValue().get(lockKey))) { //确保释放的是自己的锁
                stringRedisTemplate.delete(lockKey); //释放锁
            }
            */
            lock.unlock();
        }
        return "end";
    }
}
