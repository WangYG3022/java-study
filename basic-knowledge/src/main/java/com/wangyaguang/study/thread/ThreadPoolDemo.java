package com.wangyaguang.study.thread;

import org.apache.commons.lang3.concurrent.BasicThreadFactory.Builder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: 线程池学习
 * @author: WANG Y.G.
 * @time: 2020/01/28 22:21
 * @version: V1.0
 */
public class ThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(Integer.MAX_VALUE),
                        new Builder().build());

        for (int i = 0; i < 15; i++) {
            final int n = i;
            threadPoolExecutor.execute(() -> {
                try {
                    System.out.println("开始执行" + n);
                    Thread.sleep(3000L);
                    System.out.println("执行结束" + n);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("任务提交成功" + i);
        }

        Thread.sleep(500L);
        System.out.println("当前线程池线程数量：" + threadPoolExecutor.getPoolSize());
        System.out.println("当前线程池等待数量为：" + threadPoolExecutor.getQueue().size());
        // 等待15秒，查看线程数量和队列数量（理论上，会被超出核心线程数量的线程自动销毁）
        Thread.sleep(15000L);
        System.out.println("当前线程池线程数量：" + threadPoolExecutor.getPoolSize());
        System.out.println("当前线程池等待数量为：" + threadPoolExecutor.getQueue().size());
    }
}
