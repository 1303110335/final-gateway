///**
// * bianque.com
// * Copyright (C) 2013-2021 All Rights Reserved.
// */
//package com.xuleyan.finals.web.advice;
//
//import com.xuleyan.finals.web.annotation.Encryption;
//import com.xuleyan.finals.web.config.EncryptionHttpInputMessage;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.IOUtils;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.HttpInputMessage;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.reflect.Type;
//
///**
// *
// * @author xuleyan
// * @version EncryptRequestBodyAdvice.java, v 0.1 2021-07-19 5:57 下午
// */
//@ControllerAdvice
//@Slf4j
//public class EncryptRequestBodyAdvice implements RequestBodyAdvice {
//
//    protected static final String CHARSET_ENCODING = "UTF-8";
//
//    @Override
//    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
//        return methodParameter.getMethod().isAnnotationPresent(Encryption.class);
//    }
//
//    @Override
//    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
//        String body = IOUtils.toString(httpInputMessage.getBody(), CHARSET_ENCODING);
//        //System.out.println("advice : beforeBodyRead" + body);
//        InputStream inputStream = IOUtils.toInputStream(body, CHARSET_ENCODING);
//        return new EncryptionHttpInputMessage(inputStream, httpInputMessage.getHeaders());
//    }
//
//    @Override
//    public Object afterBodyRead(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
//        return body;
//    }
//
//    @Override
//    public Object handleEmptyBody(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
//        return body;
//    }
//}