package com.example.demo.service.impl;

import com.example.demo.client.RedisClient;
import com.example.demo.service.SayHelloService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author ligegang
 * @title: SayHelloServiceImpl
 * @projectName springBootDemo
 * @description: TODO
 * @date 2019/11/13 15:53
 */
@Service
@Slf4j
public class SayHelloServiceImpl implements SayHelloService {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RedisClient redisClient;

    @Override
    @Retryable(value = NullPointerException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 1.5))
    public String sayHello(String name) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间:" + dateTimeFormatter.format(LocalDateTime.now()));
        System.out.println("hello world~~" + name);
        List<String> names = null;
        // names.size();
        if (!"gang".equalsIgnoreCase(name)) {
            System.out.println(3 / 0);
        }
        return "hello world~~";
    }


}
