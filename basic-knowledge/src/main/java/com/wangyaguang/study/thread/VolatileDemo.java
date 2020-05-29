package com.wangyaguang.study.thread;

/**
 * @description: volatile关键字学习
 * @author: WANG Y.G.
 * @time: 2020/05/18 11:02
 * @version: V1.0
 */
public class VolatileDemo {
    public static void main(String[] args) {
        int limit = 20;
        MyTask task = new MyTask();
        task.start();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < limit; i++) {
            synchronized (task) {
                if (task.isFlag()) {
                    System.out.println("dodododododo");
                }
            }
        }
    }
}

class MyTask extends Thread {

    private volatile boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag = " + flag);
    }
}