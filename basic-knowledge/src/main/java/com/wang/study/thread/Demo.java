package com.wang.study.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/02/03 16:46
 * @version: V1.0
 */
public class Demo {

    public static void main(String[] args) throws InterruptedException {
//        blockQueueTest();
        reEntrantLockTest();
    }

    private static void blockQueueTest() throws InterruptedException {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("123");
        queue.offer("456");
        queue.offer("789");

        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
    }

    private static void reEntrantLockTest() {

        Thread t1 = new Thread(Demo::task, "t1");
        Thread t2 = new Thread(Demo::task, "t2");
        t1.start();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
        System.out.println("main");
    }

    private static ReentrantLock lock = new ReentrantLock(true);
    private static void task() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " running...");
        } finally {
            lock.unlock();
        }
    }
}
