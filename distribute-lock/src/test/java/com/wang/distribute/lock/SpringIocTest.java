package com.wang.distribute.lock;

import com.wang.distribute.lock.controller.StockController;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description: Spring IoC学习笔记
 * @author: WANG Y.G.
 * @time: 2020/06/02 16:40
 * @version: V1.0
 */
public class SpringIocTest {

    @Test
    public void test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DistributeLockApplication.class);
        StockController stockController = context.getBean(StockController.class);
    }
}
