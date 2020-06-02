package com.wang.study.io;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description: 模拟tomcat7使用bio
 * @author: WANG Y.G.
 * @time: 2020/05/05 22:57
 * @version: V1.0
 */
public class BioHttpServer {

    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    static {
        // 每隔3秒钟打印活跃线程数
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("当前活跃线程数：" + THREAD_POOL_EXECUTOR.getActiveCount());
            }
        }, 0, 3000L);
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println(Thread.currentThread().getName() + "启动" + serverSocket.getLocalPort());

        while (true) {
            Socket socket = serverSocket.accept();
            THREAD_POOL_EXECUTOR.submit(() -> {
                // 连接没断开，随时可能传数据
                while (true) {
                    byte[] requestBody = new byte[1024];
                    socket.getInputStream().read(requestBody);
                    String request = new String(requestBody);
                    System.out.println("request = " + request);

                    String responseContent = "HTTP/1.1 200 OK\r\nContent-Length: 11\r\n\r\nHello World";
                    socket.getOutputStream().write(responseContent.getBytes());
                    socket.getOutputStream().flush();
                }
            });
        }
    }
}
