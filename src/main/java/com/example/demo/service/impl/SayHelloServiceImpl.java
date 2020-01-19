package com.example.demo.service.impl;

import com.example.demo.service.SayHelloService;
import org.springframework.stereotype.Service;

/**
 * @author ligegang
 * @title: SayHelloServiceImpl
 * @projectName springBootDemo
 * @description: TODO
 * @date 2019/11/13 15:53
 */
@Service
public class SayHelloServiceImpl implements SayHelloService {

    @Override
    public String sayHello(String name) {
        System.out.println("hello world~~" + name);
        return "hello world~~";
    }
}
