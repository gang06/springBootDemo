package com.example.demo.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo.vo.LoanBaseInfoVO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ligegang
 * @title: SystemLogAspect
 * @projectName springBootDemo
 * @description: TODO
 * @date 2019/11/13 15:46
 */
/*@Aspect
@Component*/
public class SystemLogAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* com.example.demo.service..*(..))")
    public void addAdvice(){}

    @Before("addAdvice()")
    public void doBefore(JoinPoint joinPoint){
        logger.info("前置通知");
        //获取目标方法的参数信息
        Object[] obj = joinPoint.getArgs();
        //AOP代理类的信息
        joinPoint.getThis();
        //代理的目标对象
        joinPoint.getTarget();
        //用的最多 通知的签名
        Signature signature = joinPoint.getSignature();
        //代理的是哪一个方法
        logger.info("代理的是哪一个方法"+signature.getName());
        //AOP代理类的名字
        logger.info("AOP代理类的名字"+signature.getDeclaringTypeName());
        //AOP代理类的类（class）信息
        signature.getDeclaringType();
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        //如果要获取Session信息的话，可以这样写：
        //HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
        //获取请求参数
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String,String> parameterMap = new HashMap<>();
        while (enumeration.hasMoreElements()){
            String parameter = enumeration.nextElement();
            parameterMap.put(parameter,request.getParameter(parameter));
        }
        String str = JSON.toJSONString(parameterMap);
        if(obj.length > 0) {
            logger.info("请求的参数信息为："+str);
        }

    }

    @AfterReturning(returning = "response",pointcut = "addAdvice()")
    public void  doAfterReturning(Object response){
        logger.info("执行doAfterReturning");
        logger.info(JSON.toJSONString(response,SerializerFeature.WriteMapNullValue));
    }

    @After("addAdvice()")
    public void  doAfter(){
        logger.info("执行doAfter");
    }


    @Around("addAdvice()")
    public Object doAround(){
        logger.info("执行doAround");
        LoanBaseInfoVO baseInfoVO = new LoanBaseInfoVO();
        baseInfoVO.setCustName("just do it");
        return "hahaheihei";
    }
}
