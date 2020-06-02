package com.wang.study.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/05/05 23:51
 * @version: V1.0
 */
public class NioHttpServer {

    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    static {
        // 每隔3秒钟打印活跃线程数
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("当前活跃线程数：" + THREAD_POOL_EXECUTOR.getActiveCount());
            }
        }, 0, 1000L);
    }

    public static ServerSocketChannel serverSocketChannel;
    private static Selector selector;
    public static void main(String[] args) throws IOException {
        // NIO
        serverSocketChannel = ServerSocketChannel.open();
        // 切换NIO非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 绑定8080端口
        serverSocketChannel.bind(new InetSocketAddress(8080));
        System.out.println("NIO服务器启动" + serverSocketChannel.getLocalAddress());
        //
        selector = Selector.open();
        // 登记。表示对8080端口accept事件感兴趣，帮我查询监听有没有对应的网络事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            selector.select(1000L);
            // 查询结果
            Set<SelectionKey> results = selector.selectedKeys();

            Iterator<SelectionKey> iterator = results.iterator();
            // 遍历处理每一个事件
            while (iterator.hasNext()) {
                SelectionKey result = iterator.next();
                if (result.isAcceptable()) { // SelectionKey.OP_ACCEPT，新连接建立时
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (result.isReadable()){ // SelectionKey.OP_READ，连接有数据传输时
                    SocketChannel socketChannel = (SocketChannel) result.channel();
                    result.cancel(); // 取消selector队该连接的监听，因为安排线程处理中，不需要重复处理
                    // 有数据传输时，交由线程池处理请求
                    THREAD_POOL_EXECUTOR.submit(() -> {
                        // 处理请求内容
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        socketChannel.read(byteBuffer);
                        byteBuffer.flip();
                        String request = new String(byteBuffer.array());
                        // 处理请求
//                        System.out.println("request = " + request);
                        // 返回响应
                        String responseContent = "HTTP/1.1 200 OK\r\nContent-Length: 11\r\n\r\nHello World";
                        socketChannel.write(ByteBuffer.wrap(responseContent.getBytes()));
                        // 处理完毕后，不关闭连接。继续交由selector监听
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        return null;
                    });
                }
                // 删除处理过的请求
                iterator.remove();
            }
        }
    }
}
