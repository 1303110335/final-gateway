/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.advice;

import com.xuleyan.finals.web.annotation.Encryption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 *
 * @author xuleyan
 * @version EncryptResponseBodyAdvice.java, v 0.1 2021-07-19 5:45 下午
 */
@ControllerAdvice
@Slf4j
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<String> {


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.getMethod() != null && methodParameter.getMethod().isAnnotationPresent(Encryption.class);
    }

    @Override
    public String beforeBodyWrite(String responseBody, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        System.out.println("advice : responseBody:" + responseBody);
        Encryption methodAnnotation = methodParameter.getMethodAnnotation(Encryption.class);
        System.out.println("advice : responseBody:" + methodAnnotation.requestStep());
        return responseBody;
    }
}