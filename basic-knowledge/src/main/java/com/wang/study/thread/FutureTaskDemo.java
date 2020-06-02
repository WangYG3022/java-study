package com.wang.study.thread;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * @description: Future Callable多线程场景调用服务接口，提高性能
 * @author: WANG Y.G.
 * @time: 2020/05/09 22:42
 * @version: V1.0
 */
public class FutureTaskDemo {

    private static RemoteCallProcedure remoteCallProcedure = new RemoteCallProcedure();

    private static ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(100),
                    new BasicThreadFactory.Builder().build());

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LocalDateTime start = LocalDateTime.now();
        Future<JSONObject> userTask = threadPoolExecutor.submit(() -> remoteCallProcedure.getUserInfo(10));
        Future<JSONObject> bonusTask = threadPoolExecutor.submit(() -> remoteCallProcedure.getBonusInfo(10));
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(userTask.get());
        jsonObject.putAll(bonusTask.get());
        System.out.println(jsonObject);
        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start, end);
        System.out.println("duration = " + duration.toMillis());
        threadPoolExecutor.shutdown();
    }
}

class RemoteCallProcedure {

    public JSONObject getUserInfo(Integer id) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8080/user/find/" + id);
        try {
            Thread.sleep(1100L);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            return JSONObject.parseObject(EntityUtils.toString(httpEntity));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public JSONObject getBonusInfo(Integer id) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8080/user/point/" + id);
        try {
            Thread.sleep(2500L);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            return JSONObject.parseObject(EntityUtils.toString(httpEntity));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}