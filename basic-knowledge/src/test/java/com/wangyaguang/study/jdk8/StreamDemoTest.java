package com.wangyaguang.study.jdk8;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import com.wangyaguang.study.jdk8.Person.Status;

public class StreamDemoTest {

    private List<Person> people = Arrays.asList(
            new Person("张三", 18, 1000, Status.FREE),
            new Person("李四", 23, 2000, Status.BUSY),
            new Person("王五", 26, 3454, Status.VOCATION),
            new Person("赵六", 31, 8888, Status.BUSY),
            new Person("田七", 22, 9999, Status.FREE)
    );

    /**
     * 获取流
     * 1. 单列集合转换成流：java.util.Collection.stream()或parallelStream()
     * 2. 数组转换成流：java.util.stream.Stream.of(T...)或java.util.Arrays的stream(T[])
     * 3. 创建无限流
     *      1) 迭代：Stream.iterate()
     *      2) 生成：Stream.generate()
     */
    @Test
    public void test1() {
        Stream<Person> stream = people.stream();
        Stream<Person> parallelStream = people.parallelStream();
        String[] strings = new String[]{"a", "b", "c"};
        Stream<String> stringStream = Stream.of(strings);
        Stream<String> stringStream1 = Arrays.stream(strings);
        Stream<Integer> iterate = Stream.iterate(0, (s) -> s + 2);
        iterate.filter((x) -> x % 2 == 0)
               .limit(10)
               .forEach(System.out::println);
        Stream<String> generate = Stream.generate(() -> "hello, " + Math.random());
        generate.limit(10).forEach(System.out::println);
    }

    /**
     * 中间操作
     */
    @Test
    public void test2() {
        // filter
        people.stream()
                .filter((person -> person.getAge() > 20))
                .limit(3)
                .skip(1)
                .sorted((p1, p2) -> p2.getAge() - p1.getAge())
                .map(Person::getName)
                .forEach((System.out::println));
    }

    @Test
    public void test3() {
    }
}