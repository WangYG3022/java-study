package com.wangyaguang.study.io;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.concurrent.CyclicBarrier;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/05/05 23:24
 * @version: V1.0
 */
public class BenchMarkTest {
    public static void main(String[] args) {
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(200);
        manager.setDefaultMaxPerRoute(100);

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(manager).build();

        CyclicBarrier cyclicBarrier = new CyclicBarrier(20);
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                CloseableHttpResponse response = null;
                try {
                    cyclicBarrier.await();
                    response = httpClient.execute(new HttpGet("http://localhost:8080/"));
                    System.out.println(response.getStatusLine());
                    response.getEntity().getContent().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        }
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
