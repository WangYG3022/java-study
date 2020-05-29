package com.wang.distribute.lock.zk;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @description: zookeeper实现分布式锁
 * @author: WANG Y.G.
 * @time: 2020/05/21 17:33
 * @version: V1.0
 */
public class ZookeeperLock implements Lock {

    private ZkClient zkClient;
    private String lockKey;
    private String lockValue;

    /**
     * 当前节点名称
     */
    private String curNode;
    /**
     * 前一节点名称
     */
    private String preNode;

    public ZookeeperLock(ZkClient zkClient, String lockKey) {
        this.zkClient = zkClient;
        this.lockKey = "/lock/" + lockKey;
        this.lockValue = Thread.currentThread().getName();
        if (!zkClient.exists(this.lockKey)) {
            try {
                zkClient.createPersistent(this.lockKey);
            } catch (ZkNodeExistsException e) {
                System.out.println("线程" + lockValue + "创建节点" + this.lockKey + "失败，节点已存在");
            }
        }
    }

    @Override
    public void lock() {
        if (!tryLock()) {
            // 没有获得锁，阻塞
            waitForLock();
            lock();
        }
    }

    private void waitForLock() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println(dataPath + "节点数据更新为：" + data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println(dataPath + "节点删除了");
                countDownLatch.countDown();
            }
        };
        // 注册前一节点的监听
        zkClient.subscribeDataChanges(preNode, listener);
        // 阻塞自己
        if (zkClient.exists(preNode)) {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 取消注册监听
        zkClient.unsubscribeDataChanges(preNode, listener);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        // 创建永久节点时，如果服务宕机，则会造成永久死锁
        // 创建临时节点，在解锁时会引发惊群效应
        if (this.curNode == null) {
            // 因此，选用创建临时顺序节点
            curNode = zkClient.createEphemeralSequential(lockKey + "/", lockValue);
        }
        // 获取锁路径下所有子节点
        List<String> children = zkClient.getChildren(lockKey);
        // 节点排序
        Collections.sort(children);
        if (curNode.equals(lockKey + "/" + children.get(0))) {
            // 当前节点是最小节点，获得锁
            return true;
        } else {
            // 否则，取到前一节点
            int curIndex = children.indexOf(curNode.substring(lockKey.length() + 1));
            preNode = lockKey + "/" + children.get(curIndex - 1);
            // 没有获得锁
            return false;
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        zkClient.delete(curNode);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
