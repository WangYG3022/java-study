package com.wangyaguang.study.basic;

import java.util.Scanner;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2019/11/01 15:47
 */
public class ScannerDemo {
    
    private static int input(Scanner scanner, int i) {
        System.out.println("请输入第" + i + "个数：");
        return scanner.nextInt();
    }
    
    private static int getMaxNum(int[] arr) {
        return arr[0] > arr[1] ? arr[0] : arr[1];
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arr = new int[2];
        for (int i = 1; i < 3; i++) {
            arr[i-1] = input(scanner, i);
        }
        int maxNum = getMaxNum(arr);
        System.out.println(maxNum);
    }
}
