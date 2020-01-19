package com.example.demo.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ligegang
 * @title: OperationRecordInterceptor
 * @projectName springBootDemo
 * @description: TODO
 * @date 2019/11/15 16:23
 */

public class OperationRecordInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Class handlerClass = handler.getClass();

        logger.info(handlerClass.getName());
        logger.info(handlerClass.getAnnotations().toString());



        return true;
    }
}
