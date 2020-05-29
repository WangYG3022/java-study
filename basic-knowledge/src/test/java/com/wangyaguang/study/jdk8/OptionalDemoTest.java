package com.wangyaguang.study.jdk8;

import org.junit.jupiter.api.Test;

import java.util.Optional;

public class OptionalDemoTest {

    @Test
    public void test1() {
        // of(T t) 创建一个实例
        Optional<Person> optional = Optional.of(new Person());
        Person person = optional.get();
        System.out.println(person);

    }
}