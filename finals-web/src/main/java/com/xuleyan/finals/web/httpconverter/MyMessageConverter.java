///**
// * xuleyan.com
// * Copyright (C) 2013-2021 All Rights Reserved.
// */
//package com.xuleyan.finals.web.httpconverter;
//
//import org.springframework.http.HttpInputMessage;
//import org.springframework.http.HttpOutputMessage;
//import org.springframework.http.converter.AbstractHttpMessageConverter;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.http.converter.HttpMessageNotWritableException;
//import org.springframework.util.StreamUtils;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
///**
// * 专门开辟一种MediaType去处理
// * @author xuleyan
// * @version MyMessageConverter.java, v 0.1 2021-07-19 9:28 下午
// */
//public class MyMessageConverter extends AbstractHttpMessageConverter<String> {
//    @Override
//    protected boolean supports(Class<?> clazz) {
//        return true;
//    }
//
//    @Override
//    protected String readInternal(Class<? extends String> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
//        String temp = StreamUtils.copyToString(inputMessage.getBody(), StandardCharsets.UTF_8);
//        //System.out.println("readInternal" + temp);
//        return temp;
//    }
//
//    @Override
//    protected void writeInternal(String s, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
//        //System.out.println("writeInternal:" + s);
//        outputMessage.getBody().write(s.getBytes());
//    }
//}