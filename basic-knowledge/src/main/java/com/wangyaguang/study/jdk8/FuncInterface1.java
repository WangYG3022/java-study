package com.wangyaguang.study.jdk8;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/04/16 10:45
 * @version: V1.0
 */
@FunctionalInterface
public interface FuncInterface1 {
    /**
     * 返回字符串的大写
     * @param name
     * @return
     */
    String greeting(String name);
}
