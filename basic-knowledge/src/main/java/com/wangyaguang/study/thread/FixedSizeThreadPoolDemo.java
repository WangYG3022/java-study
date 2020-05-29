package com.wangyaguang.study.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/02/26 16:49
 * @version: V1.0
 */
public class FixedSizeThreadPoolDemo {

    // 1.需要一个任务仓库
    private BlockingQueue<Runnable> blockingQueue;

    // 2.集合容器，存放工作线程
    private List<Thread> workers;

    // 3.普通线程要执行多个task
    public static class Worker extends Thread {

        private FixedSizeThreadPoolDemo pool;

        public Worker(FixedSizeThreadPoolDemo pool) {
            this.pool = pool;
        }

        @Override
        public void run() {
            while (this.pool.isWorking || this.pool.blockingQueue.size() > 0) {
                Runnable task = null;

                try {
                    // 如果没有任务，就阻塞等待任务
                    if (this.pool.isWorking) {
                        task = this.pool.blockingQueue.take();
                    } else {
                        task = this.pool.blockingQueue.poll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (task != null) {
                    task.run();
                }
            }
        }
    }

    // 4.初始化线程池
    public FixedSizeThreadPoolDemo(int poolSize, int queueSize) {
        if (poolSize <= 0 || queueSize <= 0) {
            throw new IllegalArgumentException("非法参数");
        }
        this.blockingQueue = new LinkedBlockingQueue<>(queueSize);
        this.workers = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < poolSize; i++) {
            Worker worker = new Worker(this);
            worker.start();
            workers.add(worker);
        }
    }

    // 对外提供提供任务的接口，非阻塞
    public boolean submit(Runnable task) {
        if (isWorking) {
            return this.blockingQueue.offer(task);
        } else {
            return false;
        }
    }

    // 对外提供提供任务的接口，阻塞
    public void execute(Runnable task) {
        try {
            if (isWorking) {
                this.blockingQueue.put(task);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private volatile boolean isWorking = true;

    /**
     * 关闭线程池
     * a. 禁止往队列提交任务
     * b. 等待仓库中的任务执行
     * c. 关闭的时候，再去拿任务就不会阻塞，因为不会有新任务来了
     * d. 关闭的时候，阻塞的线程要强制中断
     */
    public void shutdown() {
        this.isWorking = false;
        for (Thread thread : workers) {
            if (thread.getState().equals(Thread.State.WAITING) ||
                    thread.getState().equals(Thread.State.TIMED_WAITING)) {
                try {
                    if (!thread.isInterrupted()) {
                        thread.interrupt();
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
