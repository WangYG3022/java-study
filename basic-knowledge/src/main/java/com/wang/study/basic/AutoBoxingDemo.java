package com.wang.study.basic;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @description: 自动装箱类型性能demo
 * @author: WANG Y.G.
 * @time: 2020/02/03 21:07
 * @version: V1.0
 */
public class AutoBoxingDemo {
    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.now();
//        Long sum = 0L;
        long sum = 0L;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            /*
             * sum为装箱类型Long时，每次+=运算都会创建一个新对象，性能较差
             * sum为基本类型long时，不会创建新对象，性能好
             */
            sum += i;
        }
        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start, end);
        System.out.println("耗时（毫秒）：" + duration.toMillis());
    }
}
