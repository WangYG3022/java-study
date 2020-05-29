package com.wangyaguang.study.jdk8;

import java.util.*;
import java.util.stream.Stream;

/**
 * @description: java.util.stream.Stream接口学习
 * @author: WANG Y.G.
 * @time: 2020/03/09 17:31
 * @version: V1.0
 */
public class StreamDemo {
    public static void main(String[] args) {
//        getStream();
        Stream<String> stream = Stream.of("张三", "李四", "王五", "赵六", "田七");
        stream.forEach(name -> {
            if (name.startsWith("王")) {
                System.out.println(name);
            }
        });
    }

    /**
     * 获取流的方法
     * 1. 单列集合转换成流：java.util.Collection.stream()
     * 2. 数组转换成流：java.util.stream.Stream.of(T...)
     */
    private static void getStream() {
        // 1. 单列集合转换成流：java.util.Collection.stream()
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        Set<String> set = new HashSet<>();
        Stream<String> stream2 = set.stream();

        Map<String, String> map = new HashMap<>();
        Set<String> keySet = map.keySet();
        Stream<String> stream3 = keySet.stream();
        Collection<String> values = map.values();
        Stream<String> stream4 = values.stream();
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        Stream<Map.Entry<String, String>> stream5 = entrySet.stream();

        // 2. 数组转换成流：java.util.stream.Stream.of(T...)
        String[] strArr = new String[10];
        Stream<String> stream6 = Stream.of(strArr);
    }
}
