package com.jiangshuaa.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by jiangshuhua on 2017/5/9.
 */
@Aspect  // AOP : AOP(面向切面编程)Aspect Oriented Programming
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.jiangshuaa.controller.IndexController.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
        StringBuilder sb = new StringBuilder();
        for(Object arg : joinPoint.getArgs()){
            sb.append("arg:" + arg.toString() + "|");
        }
        //logger.info("before time: ", new Date());
        logger.info("before method: " + sb.toString());
    }

    @After("execution(* com.jiangshuaa.controller.IndexController.*(..))")
    public void afterMethod(JoinPoint joinPoint){
        //logger.info("after time: ", new Date());
        logger.info("after method: ");
    }
}

