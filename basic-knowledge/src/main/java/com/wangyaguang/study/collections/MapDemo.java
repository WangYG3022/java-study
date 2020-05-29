package com.wangyaguang.study.collections;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: java.util.ConcurrentModificationException
 * @author: WANG Y.G.
 * @time: 2019/11/01 16:32
 */
public class MapDemo {
    /**
     * 在对集合迭代的时候，如果同时对其进行修改就会抛出java.util.ConcurrentModificationException异常
     * @param map
     */
    private static void concurrentModificationException (Map<String, Object> map) throws ConcurrentModificationException {
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
            if (entry.getKey().endsWith("5")) {
                map.remove(entry.getKey());
            }
        }
    }
    
    /**
     * 解决方案1：
     * 单线程情况，使用迭代器，通过迭代器进行删除。
     * @param map
     */
    private static void solution_iterator(Map<String, Object> map) {
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            if (entry.getKey().endsWith("5")) {
                iterator.remove();
            }
        }
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
    
    /**
     * 解决方案2：
     * 多线程：可以使用CopyOnWriteArrayList，ConcurrentHasMap等
     * @param map
     */
    private static void solution_ConcurrentHasMap(ConcurrentHashMap<String, Object> map) {
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            if (entry.getKey().endsWith("5")) {
                map.remove(entry.getKey());
            }
        }
        Set<Map.Entry<String, Object>> set = map.entrySet();
        for (Map.Entry<String, Object> entry : set) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
    
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>();
        for (int i=0; i<10;i++) {
            map.put("user_" + i, "Tom" + i);
            concurrentHashMap.put("user_" + i, "Tom" + i);
        }
        concurrentModificationException(map); //演示问题
        solution_iterator(map); //解决方案1
        solution_ConcurrentHasMap(concurrentHashMap); //解决方案2
    }
}
