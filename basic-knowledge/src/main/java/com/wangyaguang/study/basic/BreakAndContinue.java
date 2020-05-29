package com.wangyaguang.study.basic;

/**
 * @description: break和continue的用法
 * @author: WANG Y.G.
 * @time: 2019/11/01 16:05
 */
public class BreakAndContinue {
    /**
     * break跳出单层循环
     * 打印3次后跳出循环
     */
    private static void breakUse() {
        for (int i = 0; i<10; i++) {
            if (i == 3) {
                break;
            }
            System.out.println("break" + i);
        }
    }
    
    /**
     * continue跳出本次循环，直接开始下次循环
     */
    private static void continueUse() {
        for (int i = 0; i<10; i++) {
            if (i == 3) {
                continue;
            }
            System.out.println("continue" + i);
        }
    }
    
    public static void main(String[] args) {
        breakUse();
        continueUse();
    }
}
