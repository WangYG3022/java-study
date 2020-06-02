package com.wang.study.jdk8;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/04/16 10:57
 * @version: V1.0
 */
@FunctionalInterface
public interface FuncInterface2<T, R> {
    /**
     * 两个数值运算
     * @param t1
     * @param t2
     * @return
     */
    R calculate(T t1, T t2);
}
