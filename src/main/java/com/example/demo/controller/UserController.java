package com.example.demo.controller;

import com.example.demo.service.SayHelloService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ligegang
 * @title: UserController
 * @projectName springBootDemo
 * @description: TODO
 * @date 2019/11/13 15:36
 */
@RestController
public class UserController {

    @Resource
    private SayHelloService sayHelloService;

    @RequestMapping("hello")
    public String sayHello(String name){
        String result = sayHelloService.sayHello(name);
        return result;
    }
}
