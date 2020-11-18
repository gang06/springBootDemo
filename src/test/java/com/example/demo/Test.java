package com.example.demo;


import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Test {

    public void myMethod(String name, String pwd) {
    }

    public static void main(String[] args) throws SecurityException, NoSuchMethodException {
        Method method = Test.class.getMethod("myMethod", String.class, String.class);

        for (Parameter parameter : method.getParameters()) {
            System.out.println(parameter.getName());
        }
    }

}
