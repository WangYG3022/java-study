package com.wangyaguang.study.basic;

import java.util.Arrays;

/**
 * @description: 数组
 * @author: WANG Y.G.
 * @time: 2019/11/01 17:00
 */
public class ArrayDemo {
    
    /**
     * java.lang.ArrayIndexOutOfBoundsException
     * 访问到了数组中的不存在的索引时发生
     */
    private static void arrayIndexOfBoundsException() {
        int[] arr = new int[2];
        System.out.println(arr[3]);
    }
    
    /**
     * java.lang.NullPointerException
     * 没有指向对象，却调用对象方法时发生
     */
    private static void nullPointerException() {
        String[] arr = new String[2];
        System.out.println(arr[0].length());
    }
    
    /**
     * 数组元素反转
     * @param arr
     * @return
     */
    private static int[] reverse(int[] arr) {
        if (arr != null){
            int temp;
            for (int minIndex = 0, maxIndex = arr.length - 1; minIndex < maxIndex; minIndex++, maxIndex--) {
                temp = arr[minIndex];
                arr[minIndex] = arr[maxIndex];
                arr[maxIndex] = temp;
            }
        }
        return arr;
    }
    
    public static void main(String[] args) {
        arrayIndexOfBoundsException();
        nullPointerException();
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 22, 33, 44, 55, 66};
        arr = reverse(arr);
        System.out.println(Arrays.toString(arr));
    }
}
