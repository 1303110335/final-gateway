/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 *
 * @author xuleyan
 * @version GlobalHandlerAspect.java, v 0.1 2021-07-19 8:47 下午
 */
@Aspect
@Slf4j
@Component
public class GlobalHandlerAspect {

    @Pointcut(value = "execution(public * com.xuleyan.finals.web.controller.*.*(..))")
    public void executeController() {
    }

    @Around(value = "executeController()")
    public Object preSet(ProceedingJoinPoint joinPoint) {
        Object proceed = null;
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Object[] args = joinPoint.getArgs();
            log.info("aspect: preSet >> args = {}", args);
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return proceed;
    }

}