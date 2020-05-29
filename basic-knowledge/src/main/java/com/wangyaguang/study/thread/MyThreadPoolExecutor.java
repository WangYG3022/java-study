package com.wangyaguang.study.thread;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/02/03 16:08
 * @version: V1.0
 */
public class MyThreadPoolExecutor {

    /**
     * 核心线程数
     */
    private int corePoolSize;
    /**
     * 最大线程数
     */
    private int maxPoolSize;
    /**
     * 工作仓库
     */
    private BlockingDeque<Runnable> workQueue;
    /**
     * 当前线程池大小
     */
    private AtomicInteger currentPoolSize = new AtomicInteger();

    public MyThreadPoolExecutor(int corePoolSize, int maxPoolSize, BlockingDeque<Runnable> workQueue) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.workQueue = workQueue;
    }

    public void execute(Runnable task) throws Exception {
        if (task == null) {
            throw new NullPointerException();
        }
        // 1. 判断核心线程数
        if (currentPoolSize.get() < corePoolSize) {
            // 未达到核心线程数，首先线程数加一，创建一个新核心线程执行任务
            if (currentPoolSize.incrementAndGet() <= corePoolSize) {
                new Worker(task).start();
                return;
            } else {
                // 已达到，核心线程数减一（抵消之前的加一操作），执行2
                currentPoolSize.decrementAndGet();
            }
        }
        // 2. 任务队列是否已满？没满，则将当前任务加入任务仓库
        if (workQueue.offer(task)) {
            return;
        }
        // 3. 是否达到线程池最大数量？
        if (currentPoolSize.get() < maxPoolSize) {
            // 没达到，则创建新的线程
            if (currentPoolSize.incrementAndGet() <= maxPoolSize) {
                new Worker(task).start();
                return;
            } else {
                // 超了，减一处理
                currentPoolSize.decrementAndGet();
            }
        }
        // 4. 拒绝处理
        throw new RejectedExecutionException("拒绝处理");
    }

    public class Worker extends Thread {

        private Runnable firstTask;

        public Worker(Runnable firstTask) {
            this.firstTask = firstTask;
        }

        @Override
        public void run() {
            try {
                Runnable task = firstTask;
                while (task != null || (task = workQueue.take()) != null) {
                    task.run();
                    task = null;
                }
                do {
                    task.run();
                } while ((task = workQueue.take()) != null);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("线程池中的线程出现异常!");
            }
        }
    }
}
