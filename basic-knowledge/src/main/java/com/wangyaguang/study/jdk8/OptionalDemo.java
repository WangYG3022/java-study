package com.wangyaguang.study.jdk8;

import java.util.Optional;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/04/15 14:46
 * @version: V1.0
 */
public class OptionalDemo {

    public static void main(String[] args) {
        Optional<Person> optional = Optional.of(new Person());
        Person person = optional.get();
        optional.orElseGet(Person::new);
    }
}
