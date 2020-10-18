package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ExecutorServiceConfiguration {

    private final int coreThreadNum = 10;
    private final int maxnThreadNum = 100;


    @Bean(name = "systemExecutor")
    public ExecutorService initSystemExecutor(){
        //ThreadPoolExecutor executor = new ThreadPoolExecutor(coreThreadNum,maxnThreadNum,);
        return null;
    }




}
