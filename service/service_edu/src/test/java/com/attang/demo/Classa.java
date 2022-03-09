package com.attang.demo;

import org.junit.Test;

import java.util.Map;

public class Classa {
    public static void main(String[] args) {
        int i = 2 + 3;
    }
    @Test
    public void test01() throws ClassNotFoundException {
        Animal animal = new Animal();
        Class aClass1 = animal.getClass();
        Class aClass = Class.forName("com.attang.demo.Animal");
        Class aClass2 = Class.forName("com.attang.demo.Animal");
        Class aClass3 = Class.forName("com.attang.demo.Animal");
        System.out.println(aClass.hashCode());
        System.out.println(aClass2.hashCode());
        System.out.println(aClass3.hashCode());
        System.out.println(aClass1.hashCode());
        System.out.println(animal.hashCode());
    }
}
class Animal{
    private int age;
    private String name;

    public void fly(){

    }
}
