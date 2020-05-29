package com.wang.distribute.lock.config;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;

/**
 * @description: zk配置类
 * @author: WANG Y.G.
 * @time: 2020/05/21 17:34
 * @version: V1.0
 */
@Configuration
public class ZookeeperConfig {

    @Bean
    public ZkClient zkClient() {
        ZkClient zkClient = new ZkClient("wang.aliyun.com:2181");
        zkClient.setZkSerializer(new ZkSerializer() {
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
        });
        return zkClient;
    }
}
