package com.noblel.essayjoke.model;

/**
 * @author Noblel
 */

public class Person {
    private String mName;
    private int mAge;

    //如果不创建此方法那么反射调用不了newInstance方法
    public Person() {

    }

    public Person(String name, int age) {
        mName = name;
        mAge = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + mName + '\'' +
                ", age=" + mAge +
                '}';
    }
}
