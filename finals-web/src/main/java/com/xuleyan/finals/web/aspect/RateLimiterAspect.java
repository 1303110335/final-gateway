/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.aspect;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import com.xuleyan.finals.web.annotation.GoodsLimiter;
import com.xuleyan.frame.common.result.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 *
 * @author xuleyan
 * @version RateLimiterAspect.java, v 0.1 2021-07-22 11:22 上午
 */
@Component
@Aspect
@Slf4j
public class RateLimiterAspect {

    private RateLimiter rateLimiter;

    @PostConstruct
    public void init() {
        rateLimiter = RateLimiter.create(100);
    }

    @Pointcut("@annotation(com.xuleyan.finals.web.annotation.GoodsLimiter)")
    public void executeLimiter() {

    }

    @Around("executeLimiter()")
    public Object around(ProceedingJoinPoint joinPoint) {

        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setSuccess(false);
        responseWrapper.setErrMsg("前方排队拥挤，请重试");
        responseWrapper.setErrCode("vaccine_error_4");

        GoodsLimiter goodsLimiter = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(GoodsLimiter.class);
        // 每秒限速
        int limit = goodsLimiter.limit();
        rateLimiter.setRate(limit);
        boolean acquireResult = false;
        try {
            acquireResult = rateLimiter.tryAcquire(1);
            if (acquireResult) {
                return joinPoint.proceed();
            }
        } catch (Throwable e) {
            log.error("aop失败", e);
        }
        return JSON.toJSONString(responseWrapper);
    }
}