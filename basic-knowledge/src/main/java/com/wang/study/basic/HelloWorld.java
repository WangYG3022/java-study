package com.wang.study.basic;

import java.text.MessageFormat;

/**
 * @description: HelloWorld
 * @author: WANG Y.G.
 * @time: 2019/11/01 15:42
 */
public class HelloWorld {
    
    private static final String[] season = {"冬", "冬", "春", "春", "春", "夏", "夏", "夏", "秋", "秋", "秋", "冬"};
    
    private static void getSeason(int month) {
        if (month < 1 || month > 12) {
            System.out.println("月份错误");
            return;
        }
        System.out.println(season[month - 1]);
    }
    
    public static void main(String[] args) {
        getSeason(12);
        messageFormat();
    }

    /**
     * 格式化替换字符串
     */
    public static void messageFormat() {
        String format = MessageFormat.format("你好，{0}，{1}岁", "张三", "10");
        System.out.println(format);
    }
}
