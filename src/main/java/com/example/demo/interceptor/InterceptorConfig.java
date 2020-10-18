package com.example.demo.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author ligegang
 * @title: InterceptorConfig
 * @projectName springBootDemo
 * @description: TODO
 * @date 2019/11/15 16:38
 */
//@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Bean
    public OperationRecordInterceptor getOperationRecordInterceptor(){
        return new OperationRecordInterceptor();
    }

    @Bean
    public TestInterceptor getTestInterceptor(){
        return new TestInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(getOperationRecordInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(getTestInterceptor()).addPathPatterns("/**");
    }
}
