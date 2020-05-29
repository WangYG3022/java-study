package com.wangyaguang.study.jdk8;

/**
 * @Description:
 * @Author: WANG Y.G.
 * @Time: 2020/01/13 16:15
 * @version: V1.0
 */
public class Hero implements Comparable<Hero> {

    private String name;

    private float hp;

    private int damage;

    public Hero() {
    }

    public Hero(String name, float hp, int damage) {
        this.name = name;
        this.hp = hp;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "name='" + name + '\'' +
                ", hp=" + hp +
                ", damage=" + damage +
                '}';
    }

    @Override
    public int compareTo(Hero o) {
        return 0;
    }
}
