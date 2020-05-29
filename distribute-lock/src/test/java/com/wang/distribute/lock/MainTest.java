package com.wang.distribute.lock;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/05/21 17:07
 * @version: V1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DistributeLockApplication.class)
public class MainTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final int USER_NUM = 100;

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR =
            new ThreadPoolExecutor(USER_NUM, USER_NUM, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    @Test
    public void redis() {
        CountDownLatch countDownLatch = new CountDownLatch(USER_NUM);
        for (int i = 1; i <= USER_NUM; i++) {
            THREAD_POOL_EXECUTOR.execute(() -> {
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpGet httpGet = new HttpGet("http://localhost:8080/stock/redis/11");
                try {
                    CloseableHttpResponse response = httpClient.execute(httpGet);
                    HttpEntity httpEntity = response.getEntity();
                    System.out.println(EntityUtils.toString(httpEntity));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            countDownLatch.countDown();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 阻塞主线程
        InputStream in = System.in;
        try {
            in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void zookeeper() {
        CountDownLatch countDownLatch = new CountDownLatch(USER_NUM);
        for (int i = 1; i <= USER_NUM; i++) {
            THREAD_POOL_EXECUTOR.execute(() -> {
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpGet httpGet = new HttpGet("http://localhost:8080/stock/zk/22");
                try {
                    CloseableHttpResponse response = httpClient.execute(httpGet);
                    HttpEntity httpEntity = response.getEntity();
                    System.out.println(EntityUtils.toString(httpEntity));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            countDownLatch.countDown();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 阻塞主线程
        InputStream in = System.in;
        try {
            in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void redisTest() {
        int length = LocalDate.now().lengthOfMonth();
        String login = "login";
        for (int i = 1; i <= length; i++) {
            if (i % 6 == 0) {
                stringRedisTemplate.opsForValue().setBit(login, i, false);
            } else {
                stringRedisTemplate.opsForValue().setBit(login, i, true);
            }
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 1; i <= length; i++) {
            if (stringRedisTemplate.opsForValue().getBit("login", i)) {
                buffer.append("1");
            } else {
                buffer.append("0");
            }
        }
        String log = buffer.toString();
        System.out.println(log);
        int i = Integer.parseInt(log, Character.MIN_RADIX);
        System.out.println(i);
        System.out.println(Integer.toBinaryString(i));
    }
}
