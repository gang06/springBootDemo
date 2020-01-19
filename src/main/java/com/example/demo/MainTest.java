package com.example.demo;

import sun.misc.BASE64Encoder;

/**
 * @author ligegang
 * @title: MainTest
 * @projectName springBootDemo
 * @description: TODO
 * @date 2020/1/19 17:01
 */
public class MainTest {

    public static void main(String[] args) {
        String man = "Man";
        String a = "A";
        String bc = "BC";

        BASE64Encoder encoder = new BASE64Encoder();
        System.out.println("Man base64结果为：" + encoder.encode(man.getBytes()));
        System.out.println("BC base64结果为：" + encoder.encode(bc.getBytes()));
        System.out.println("A base64结果为：" + encoder.encode(a.getBytes()));
    }
}
