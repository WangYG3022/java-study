package com.wangyaguang.study.collections;

import com.wangyaguang.study.jdk8.Hero;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @description: Collections.emptyList()
 * @author: WANG Y.G.
 * @time: 2020/01/15 10:34
 * @version: V1.0
 */
public class EmptyListDemo {
    public static void main(String[] args) {
        /*
         * 通过java.util.Collections.emptyList()方法的相关源码可以得知它实际上就是返回了一个空的List，
         * 但是这个List和我们平时常用的那个List是不一样的。这个方法返回的List是Collections类的一个静态内部类，
         * 它继承AbstractList后并没有实现add()、remove()等方法，因此这个返回值List并不能增加删除元素。
         * 既然这个List不能进行增删操作，那么它有何意义呢？
         * 这个方法主要目的就是返回一个不可变的列表，使用这个方法作为返回值就不需要再创建一个新对象，可以减少内存开销。
         * 并且返回一个size为0的List，调用者不需要校验返回值是否为null，所以建议使用这个方法返回可能为空的List。
         * emptySet()、emptyMap()方法同理。
         */
//        List<Hero> list = Collections.emptyList();
        ArrayList<Hero> list = new ArrayList<>();
        Hero hero = new Hero("zhang", 100, 50);
        list.add(hero);
    }
}
