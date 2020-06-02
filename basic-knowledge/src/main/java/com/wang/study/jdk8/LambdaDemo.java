package com.wang.study.jdk8;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Description: Lambda表达式学习
 * @Author: WANG Y.G.
 * @Time: 2020/01/13 16:12
 * @version: V1.0
 */
public class LambdaDemo {
    public static void main(String[] args) {
        Random random = new Random();
        // 初始化列表
        List<Hero> heroList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            heroList.add(new Hero("H" + i, random.nextInt(1000), random.nextInt(100)));
        }
        filter(heroList, hero -> (hero.getHp() > 200 && hero.getDamage() < 50));
    }

    public static void filter(List<Hero> heroList, HeroChecker checker) {
        for (Hero hero : heroList) {
            if (checker.test(hero)) {
                System.out.println(hero);
            }
        }
    }


}
