package com.wangyaguang.study.basic;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/02/04 14:25
 * @version: V1.0
 */
public class EnumDemo {
    public static void main(String[] args) {
        System.out.println(DataBaseEnum.ORACLE_11G.name());
        System.out.println(DataBaseEnum.ORACLE_11G.getType());
        System.out.println(DataBaseEnum.ORACLE_11G.getVersion());
    }
}
