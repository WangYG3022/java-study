package com.wangyaguang.study.basic;

import java.util.Random;
import java.util.Scanner;

/**
 * @description: java.util.Random学习
 * @author: WANG Y.G.
 * @time: 2019/11/01 16:12
 */
public class RandomDemo {
    /**
     * 系统产生一个1-100之间的随机数，请猜出这个数据是多少
     * @param target 正确答案
     */
    private static void guess(int target) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入一个1~100之间的整数：");
        int guess = scanner.nextInt();
        while (guess != target) {
            if (guess > target) {
                System.out.println("大了");
            }
            if (guess < target) {
                System.out.println("小了");
            }
            System.out.println("请重新输入：");
            guess = scanner.nextInt();
        }
        System.out.println("恭喜您答对了！");
        scanner.close();
    }
    
    public static void main(String[] args) {
        Random random = new Random();
        int target = random.nextInt(100) + 1; //random.nextInt(100) ==> 0<=x<100
        guess(target);
    }
}
