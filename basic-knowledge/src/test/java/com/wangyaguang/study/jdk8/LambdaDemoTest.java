package com.wangyaguang.study.jdk8;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.*;

public class LambdaDemoTest {

    private List<Person> people = Arrays.asList(
            new Person("张三", 18, 1000),
            new Person("李四", 23, 2000),
            new Person("王五", 23, 3454),
            new Person("赵六", 31, 8888),
            new Person("田七", 22, 9999)
    );

    /**
     * jdk8 内置4大核心函数式接口
     * <ol>Consumer&lt;T&gt;  void accept(T t); 消费型接口</ol>
     * <ol>Supplier&lt;T&gt; T get(); 供给型接口</ol>
     * <ol>Function&lt;T, R&gt; R apply(T t); 功能型接口</ol>
     * <ol>Predicate&lt;T&gt; boolean test(T t); 断言型接口</ol>
     *
     * 其余见java.util.function包
     */
    @Test
    void test1() {
        happy(100, (m) -> System.out.println("吃饭花了" + m + "元。"));
        getNumList(10, () -> (int) (Math.random() * 100)).forEach(System.out::println);
        System.out.println(function("jdhsgflrjiaskjdskldajhf", (s) -> s.substring(1, 5).toUpperCase()));
        System.out.println(predicate(people.get(1), (age) -> age > 25 && age < 35));
    }

    private boolean predicate(Person p, Predicate<Integer> predicate) {
        return predicate.test(p.getAge());
    }

    private String function(String s, Function<String, String> function) {
        return function.apply(s);
    }

    private List<Integer> getNumList(int num, Supplier<Integer> supplier) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    private void happy(double money, Consumer<Double> consumer) {
        consumer.accept(money);
    }

    /**
     * 按年龄升序、工资降序排序
     */
    @Test
    public void  test2() {
        Collections.sort(people, (p1, p2) -> {
            if (p1.getAge() == p2.getAge()) {
                return (int) (p2.getSalary() - p1.getSalary());
            }
            return p1.getAge() - p2.getAge();
        });

        people.forEach(System.out::println);
    }

    @Test
    public void test3() {
        FuncInterface1 f1 = (s) -> "Hello, " + s.toUpperCase() + "!";
        String value = f1.greeting("tom");
        System.out.println(value);

        FuncInterface2<Long, Long> f2 = Long::sum;
        System.out.println(f2.calculate(2565L, 1233L));
        FuncInterface2<Long, Long> f3 = (l1, l2) -> l1 * l2;
        System.out.println(f3.calculate(1234L, 4567L));
    }

    /**
     * 方法引用
     */
    @Test
    public void test4() {
        people.forEach(this::greeting);
    }

    private void greeting(Person person) {
        /**
         * lambda体中调用方法的参数列表和返回值类型与函数式接口中抽象方法的参数列表和返回值类型保持一致时可以使用方法引用
         */
        FuncInterface3 func = person::getName;
        System.out.println("hello, " + func.getName());
    }

    @Test
    public void test5() {
        /**
         * 类型 :: 实例方法名
         * 当lambda表达式需要两个参数，第一个参数为实例方法的调用者，第二个参数（或无参）为该实例方法的参数时，可以使用 类::实例方法名 的写法
         */
        FuncInterface4 f4 = Person::getName;
        for (Person person : people) {
            System.out.println(f4.getName(person));
        }
        BiPredicate<String, String> biPredicate = String::equals;
        boolean test = biPredicate.test("aaa", "bbb");
        System.out.println(test);
    }

    @Test
    public void test6() {
        /**
         * 构造器引用。构造器的参数列表与函数式接口的参数列表一致
         */
        Supplier<Person> supplier = Person::new;
        System.out.println(supplier.get());
        /**
         * 数组引用。等价于 lambda 表达式 x -> new int[x]。其中类型可以为基本类型也可以是类。
         */
        Function<Integer, String[]> function = String[]::new;
        System.out.println(function.apply(10).length);
        Function<Integer, int[]> function1 = int[]::new;
    }
}