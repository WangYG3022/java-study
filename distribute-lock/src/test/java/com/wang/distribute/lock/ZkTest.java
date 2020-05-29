package com.wang.distribute.lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/05/21 22:31
 * @version: V1.0
 */
public class ZkTest {
    public static void main(String[] args) {
        final String zkServer = "wang.aliyun.com:2181";
        /*ZkClient zkClient = new ZkClient(zkServer);
        ZkSerializer zkSerializer = new ZkSerializer() {

            String charset = "UTF-8";

            @Override
            public byte[] serialize(Object o) throws ZkMarshallingError {
                try {
                    return String.valueOf(o).getBytes(charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    throw new ZkMarshallingError(e);
                }
            }

            @Override
            public Object deserialize(byte[] bytes) throws ZkMarshallingError {
                try {
                    return new String(bytes, charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    throw new ZkMarshallingError(e);
                }
            }
        };
        zkClient.setZkSerializer(zkSerializer);
        zkClient.subscribeDataChanges("/lock/product_11", new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println(dataPath + "节点数据变化：" + data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println(dataPath + "节点被删除了");
            }
        });

        try {
            Thread.sleep(1000L * 60 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        /*
         * 博客地址：http://throwable.club/2018/12/16/zookeeper-curator-usage/
         * ZAB协议：https://www.jianshu.com/p/2bceacd60b8a
         * ZooKeeper的Leader选举：https://www.cnblogs.com/leesf456/p/6107600.html
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(zkServer)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        try {
            // 1. 创建节点，无初始值
            client.create().forPath("/lock1");
            // 2. 创建节点并初始值
            client.create().forPath("/lock2", "lock2".getBytes());
            // 3. 创建节点，并指定节点类型
            client.create().withMode(CreateMode.EPHEMERAL).forPath("/lock3", "lock3".getBytes());
            // 4. 创建节点，指定节点类型，并自动递归创建父节点
            client.create()
                    .creatingParentContainersIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath("/lock4/abc", "abc".getBytes());
            // 5. 删除节点
            client.delete().forPath("/lock");
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.close();
    }
}
